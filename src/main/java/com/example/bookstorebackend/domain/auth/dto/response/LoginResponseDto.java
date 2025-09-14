package com.example.bookstorebackend.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "LoginResponse", description = "로그인 응답 DTO (Access/Refresh 토큰)")
public class LoginResponseDto {

    @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzI1NiIsInR...")
    private final String accessToken;

    @Schema(description = "JWT Refresh Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI...")
    private final String refreshToken;

    public static LoginResponseDto createFromSignup(String accessToken, String refreshToken) {
        return new LoginResponseDto(
                accessToken,
                refreshToken
        );
    }
}

