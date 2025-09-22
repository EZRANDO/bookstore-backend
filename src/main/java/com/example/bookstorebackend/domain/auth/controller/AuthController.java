package com.example.bookstorebackend.domain.auth.controller;

import com.example.bookstorebackend.domain.auth.dto.request.LoginRequestDto;
import com.example.bookstorebackend.domain.auth.dto.response.LoginResponseDto;
import com.example.bookstorebackend.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "로그인 / 로그아웃 / 토큰 재발급")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "자격 증명 검증 후 Access/Refresh 토큰 발급")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);

        //쿠키를 생성하기 위한 refreshToken
        String refreshToken = loginResponseDto.getRefreshToken();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true) //이렇게 HttpOnly설정을 해두면, XSS공격을 막을 수 있다. 만약, 설정해놓지 않는다면 자바스크립트로 쿠키를 읽음-> Token탈취가 가능하다.
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok(loginResponseDto);

    }

    //쿠키가 RefreshToken이 들어있어 로그아웃시 삭제하지 않으면 토큰 재발급 공격에 노출될 확률 높음.
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "AccessToken 블랙리스트 처리 및 Refresh 쿠키 삭제")
    @ApiResponse(responseCode = "204", description = "로그아웃 성공")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader,
                                       HttpServletResponse response) {

        //로그아웃은 인증된 사용자만 수행 가능. 그래서 AccessToken으로 파악.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("AccessToken이 필요합니다.");
        }

        String accessToken = Objects.requireNonNull(authorizationHeader).substring(7);
        authService.logout(accessToken);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "만료(임박) AccessToken + RefreshToken으로 재발급")
    @ApiResponse(responseCode = "200", description = "재발급 성공")
    public ResponseEntity<LoginResponseDto> reissue(@RequestHeader("Authorization") String authorizationHeader,
                                            @CookieValue(value = "refreshToken", required = false)String refreshToken,
                                            HttpServletResponse response) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("AccessToken이 필요합니다.");
        }

        String accessToken = authorizationHeader.substring(7);
        LoginResponseDto loginResponseDto = authService.reissue(accessToken, refreshToken);

        // 새 RefreshToken을 쿠키에 다시 저장
        ResponseCookie newCookie = ResponseCookie.from("refreshToken", loginResponseDto.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, newCookie.toString());

        return ResponseEntity.ok(loginResponseDto);
    }
}
