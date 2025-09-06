package com.example.bookstorebackend.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {

    private final String accessToken;

    private final String refreshToken;

    public static LoginResponseDto createFromSignup(String accessToken, String refreshToken) {
        return new LoginResponseDto(
                accessToken,
                refreshToken
        );
    }
}

