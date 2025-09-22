package com.example.bookstorebackend.domain.user.dto.response;

import com.example.bookstorebackend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@Schema(name = "UserResponse", description = "사용자 조회 응답 DTO")
public class UserResponseDto {

    @Schema(description = "사용자 ID", example = "1")
    private final Long userId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private final String name;

    @Schema(description = "사용자 이메일", example = "hong@test.com")
    private final String email;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
