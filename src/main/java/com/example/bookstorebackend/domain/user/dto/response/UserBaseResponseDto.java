package com.example.bookstorebackend.domain.user.dto.response;

import com.example.bookstorebackend.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

//유저 생성 or 업데이트시 응답
@Builder
@Getter
@Schema(name = "UserBaseResponse", description = "사용자 생성/업데이트 기본 응답 DTO")
public class UserBaseResponseDto {

    @Schema(description = "사용자 ID", example = "1")
    private final Long userId;

    public static UserBaseResponseDto from(User user) {
        return UserBaseResponseDto.builder()
                .userId(user.getId())
                .build();
    }
}

