package com.example.bookstorebackend.domain.user.dto.response;

import com.example.bookstorebackend.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

//유저 생성 or 업데이트시 응답
@Builder
@Getter
public class UserBaseResponseDto {

    private final Long id;

    public static UserBaseResponseDto from(User user) {
        return UserBaseResponseDto.builder()
                .id(user.getId())
                .build();
    }
}

