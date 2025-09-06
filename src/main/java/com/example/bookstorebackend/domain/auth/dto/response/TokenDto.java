package com.example.bookstorebackend.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDto {

    private final String accessToken;
    private final String refreshToken;

}
