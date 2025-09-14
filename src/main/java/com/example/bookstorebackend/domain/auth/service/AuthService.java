package com.example.bookstorebackend.domain.auth.service;

import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.auth.dto.request.LoginRequestDto;
import com.example.bookstorebackend.domain.auth.dto.response.LoginResponseDto;
import com.example.bookstorebackend.domain.auth.entity.RefreshToken;
import com.example.bookstorebackend.domain.auth.repository.RefreshTokenRepository;
import com.example.bookstorebackend.domain.user.entity.User;
import com.example.bookstorebackend.domain.user.repository.UserRepository;
import com.example.bookstorebackend.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;

    //로그인시 accesstoken과 refreshtoken발급
    //jwt + cookie적용은 인젝션 공격을 막는 방법이므로, 로그인 시 쿠키에 jwt담음.
    //리프레시 토큰은 무상태에 위배되니까 잘 활용하려면 서버 저장소를 잘 선택해야함. Redis를 사용하면 빠르게 토큰을 저장하고, 조회할 수 있을듯.
    //OAuth2기반 인증에서는 보통 완전히 stateless하진 않음.
    //재발급 시 기존 토큰 삭제, 로그아웃, 탈퇴 시 Refresh Token 삭제, 재사용된 RefreshToken로그 강제 종료 처리
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        //access token과 RefredshTocken 유지 시간은 조정을 어떻게 해야할까.

        //emamil같은지
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //비번 같은지
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        //로그인 정보를 바탕으로 인증 수단을 만듦
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        //토큰 생성 임시 전달용 객체. JWT생성해서 담아놓은 것.
        LoginResponseDto loginResponseDto = generateTokenDto(authentication);

        //토큰에서 저장하는 필드는 createdAt은 자동.
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(user.getId()) // 로그인한 사용자의 PK
                .token(loginResponseDto.getRefreshToken()) // 발급된 리프레시 토큰
                .build();

        refreshTokenRepository.save(refreshToken);

        return LoginResponseDto.createFromSignup(loginResponseDto.getAccessToken(), loginResponseDto.getRefreshToken());
    }

    //로그아웃시 토큰 무효화
    //쿠키 만료 처리도 같이 해야함. 쿠키는 응답의 일부니까 헤더에 담아 브라우저로 보냄.
    //로그아웃 시 현재 사용 중인 Access Token을 서버에 블랙리스트로 등록하여 재사용을 방지해야함. -> accesstoken을 저장해야함.
    @Transactional //저장할거기 때문에 RefreshToken저장이면.
    public void logout(String accessToken) {

        //엑세스 토큰 검증
        if(!jwtProvider.validateToken(accessToken)) {
            throw new CustomException(ErrorCode.TOKEN_VALIDATION_FAILED);
        }
        //엑세스 토큰에서 인증된 정보를 가져옴
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();

        //db에서 저장된 리프레시 토큰 삭제
        refreshTokenRepository.deleteById(userEmail);

        //access token블랙리스트에 등록해서 만료시킴.
        //jwtfilter에서 블랙리스트에 토큰이 있는지 확인해야함.
        long expiration = jwtProvider.getRemainingTime(accessToken);
        redisTemplate.opsForValue().set("blacklist:" + accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

    }

    //JWT서명 및 만료시간을 안전하게 생성해야 함. Jwt문자열 저장.
    //사용자 정보, 만료시간, 서명을 넣어 문자열을 생성. jwt의 3가지 파트 구성을 생성하기 위해.
    @Transactional
    public LoginResponseDto generateTokenDto(Authentication authentication) {
        String accessToken = jwtProvider.createAccessToken(authentication);
        String refreshToken = jwtProvider.createRefreshToken(authentication);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    //사용자가 요청하지 않더라도 프론트엔드에서 받아서 따로 재발급 메서드를 처리해야함.
    //프론트가 자동으로 재발급 요청을 보내주니까 그걸 받아서 처리해줄 재발급 로직이 백엔드에 반드시 있어야.
    @Transactional
    public LoginResponseDto reissue(String accessToken, String refreshToken) {

        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();

        RefreshToken savedToken = refreshTokenRepository.findById(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_MISMATCH);
        }

        String newAccessToken = jwtProvider.createAccessToken(authentication);
        String newRefreshToken = jwtProvider.createRefreshToken(authentication);

        savedToken.updateToken(newRefreshToken);

        return new LoginResponseDto(newAccessToken, newRefreshToken);

    }
}
