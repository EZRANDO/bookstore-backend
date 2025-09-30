package com.example.bookstorebackend.security.filter;

import com.example.bookstorebackend.security.jwt.JwtProvider;
import com.example.bookstorebackend.security.principal.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Setter
    private RedisTemplate<String, String> redisTemplate;

    //무조건 구현해야 함.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //가져온 값에서 접두사 제거
        String token = getAccessToken(request.getHeader("Authorization"));
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //블랙리스트 확인
        //redis에 토큰이 블랙리스트 처리 되어있는지 확인
        if (Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token))) {
            //인증 안됐으면 인증 안됐다고.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //응답 데이터 형식은 JSON
            response.setContentType("application/json");
            //혹시 모름 인코딩.
            response.setCharacterEncoding("UTF-8");
            //실제 본문.
            response.getWriter().write("{\"message\": \"유효하지 않은 토큰입니다.\"}");
            return;
        }

        if (!jwtProvider.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = jwtProvider.getUserId(token);
        if (userId == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            UserDetails userDetails = customUserDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":\"INVALID_ACCESS_TOKEN\",\"message\":\"인증 정보가 유효하지 않습니다.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader == null) return null;
        String v = authorizationHeader.trim();
        //Bearer대소문자 무시 제거
        if (v.regionMatches(true, 0, "Bearer", 0, 6)) {
            v = v.substring(6).trim();
        }
        //개행 제거
        v = v.replace("\n", "").replace("\r", "");
        return v.isEmpty() ? null : v;
    }
}

