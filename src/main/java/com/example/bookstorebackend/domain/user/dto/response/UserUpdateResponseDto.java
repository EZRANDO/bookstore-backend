package com.example.bookstorebackend.domain.user.dto.response;

import com.example.bookstorebackend.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserUpdateResponseDto {

    private final Long userId;

    private final LocalDateTime updatedAt;

    public static UserUpdateResponseDto from(User user) {
        return UserUpdateResponseDto.builder()
                .userId(user.getId())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
