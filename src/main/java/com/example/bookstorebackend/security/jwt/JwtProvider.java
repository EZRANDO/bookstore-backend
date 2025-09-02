package com.example.bookstorebackend.security.jwt;

import com.example.bookstorebackend.security.principal.CustomUserPrincipal;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    //토큰 생성
    public String createAccessToken(Authentication authentication) {
        long now = System.currentTimeMillis();

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getUserId();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("roles", roles)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtProperties.getAccessTokenExpireTime()))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
    public String createRefreshToken(Authentication authentication) {

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getUserId();

        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtProperties.getRefreshTokenExpireTime()))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    //Authentication객체 인증이 완료된 사용자의 정보를 담는 객체
    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String token) {

        Claims claims = extractClaims(token);
        String username = claims.getSubject();
        //자바가 Object타입을 List<String>으로 바꾸는 걸 확신할 수 없다. JWT내부 구조는 String , Object로 들어가 있어서 항상 Object로 반환 그래서 타입 바꿔줌.
        List<String> roles = (List<String>) claims.get("roles");
        if (roles == null) roles = Collections.emptyList();

        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //두 번째 인자에서 null -> JWT인증 이후 사용자 정보를 복원하는 상황. 서버는 비번을 알 필요는 없고, 사용자가 인증된 것인지만 알면 됨.
        return new UsernamePasswordAuthenticationToken(username, null, authorities);

    }

    //토큰이 원래 만료되는 시점에 Redis에서도 블랙리스트 정보가 자동으로 삭제되어야함.
    public long getRemainingTime(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token).getBody();
        return claims.getExpiration().getTime() - System.currentTimeMillis();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //jwt가 변조되지 않았는지, 유효 시간이 지나지 않았는지. 더 좋은 방식에 대해서는 찾지 못했음. 이와 거의 유사한 방식으로 많이 구현하는 것 같다.
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.warn("잘못된 JWT 서명입니다. {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.{}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("잘못된 JWT 서명입니다.{}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("지원하지 않는 JWT입니다.{}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 비어있습니다.{}", e.getMessage());
        }
        return false;
    }

    public Long getUserId(String token) {
        String sub = extractClaims(token).getSubject();
        return (sub == null || sub.isBlank()) ? null : Long.parseLong(sub);
    }
    public long getRefreshTokenExpireTime() {
        return jwtProperties.getRefreshTokenExpireTime();
    }
}
