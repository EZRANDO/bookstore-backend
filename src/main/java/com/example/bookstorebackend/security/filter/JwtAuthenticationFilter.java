package com.example.bookstorebackend.security.filter;

import com.example.bookstorebackend.domain.auth.dto.request.LoginRequestDto;
import com.example.bookstorebackend.domain.auth.dto.response.LoginResponseDto;
import com.example.bookstorebackend.security.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    @Setter
    private RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청 바디에서 email과 password 추출 (JSON 형태로 들어올 경우)
            LoginRequestDto loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

            // 인증 수행 (CustomUserDetailsService 호출됨)
            return getAuthenticationManager().authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException("인증 요청 처리 중 예외 발생", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        String accessToken = jwtProvider.createAccessToken(authResult);
        String refreshToken = jwtProvider.createRefreshToken(authResult);
        long refreshTokenExpireTime = jwtProvider.getRefreshTokenExpireTime();
        //토큰 저장
        redisTemplate.opsForValue().set(
                "RT:" + authResult.getName(),
                refreshToken,
                refreshTokenExpireTime,
                TimeUnit.MILLISECONDS
        );

        //토큰 세팅
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        LoginResponseDto tokenResponse = new LoginResponseDto(accessToken, refreshToken);
        String responseBody = objectMapper.writeValueAsString(tokenResponse);
        response.getWriter().write(responseBody);
    }

    //응답 구조 커스텀만.
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> failResponse = new HashMap<>();
        failResponse.put("status", "fail");
        failResponse.put("errorCode", "LOGIN_FAILED");
        failResponse.put("message", "이메일 또는 비밀번호가 올바르지 않습니다.");
        failResponse.put("path", request.getRequestURI());

        String json = objectMapper.writeValueAsString(failResponse);
        response.getWriter().write(json);
    }
}
