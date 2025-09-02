package com.example.bookstorebackend.domain.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserWithdrawalRequestDto(
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Schema(description = "사용자 계정 비밀번호")
        String password
) {}