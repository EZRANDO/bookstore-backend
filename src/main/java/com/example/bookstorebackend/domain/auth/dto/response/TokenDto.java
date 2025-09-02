package com.example.bookstorebackend.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDto {

    private String accessToken;
    private String refreshToken;

}
