package com.example.bookstorebackend.domain.user.dto.response;

import com.example.bookstorebackend.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

//유저 조회시 필요한 응답
@Builder
@Getter
public class UserResponseDto {

    private final Long id;

    private final String name;

    private final String email;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}

