package com.example.bookstorebackend.domain.user.dto.response;

import com.example.bookstorebackend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@Schema(name = "UserUpdateResponse", description = "사용자 수정 응답 DTO")
public class UserUpdateResponseDto {

    @Schema(description = "사용자 ID", example = "1")
    private final Long userId;

    @Schema(description = "수정된 시각", example = "2025-09-14T15:45:12")
    private final LocalDateTime updatedAt;

    public static UserUpdateResponseDto from(User user) {
        return UserUpdateResponseDto.builder()
                .userId(user.getId())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}