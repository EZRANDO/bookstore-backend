package com.example.bookstorebackend.common.config;

import com.example.bookstorebackend.security.filter.JwtAuthenticationFilter;
import com.example.bookstorebackend.security.filter.JwtAuthorizationFilter;
import com.example.bookstorebackend.security.jwt.JwtProvider;
import com.example.bookstorebackend.security.principal.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    //절대 바뀌지 않는 보안 정책 경로는 상수로 선언
    //actuUator/health는 서버가 살아 있는지, 정상 작동 중인지 확인하는 헬스체크 엔드포인트 쿠버네티스나 nginx가 이 경로로 호출함.
    private static final String[] PUBLIC_MATCHERS = {
            "/api/auth/**",
            "/actuator/health",
            "/error",
            "/api/users/signup"
    };

    //사용자가 누구인지 확인하는 과정 : 로그인시 refresh token저장 및 갱신
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, objectMapper);
        filter.setRedisTemplate(redisTemplate);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    //인증된 사용자가 무엇을 할 수 있는지 권한을 확인하는 과정 : access token블랙리스트  확인, 유효성 판단
    //로그인시 동작하는 필터에서 RedisTemlate은 리프레쉬 토큰을 저장하고, 기존 리프레쉬 토큰을 무효화.
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(jwtProvider, customUserDetailsService);
        filter.setRedisTemplate(redisTemplate); // 블랙리스트 체크용
        return filter;
    }

    //보안 필터 체인 구성
    //1. CRSF, Form Login, HTTP Basic비활성화
    //2. 세션 사용x
    //3. 요청 인증/인가 설정 public_matches로 정의된 url모두 허용.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
