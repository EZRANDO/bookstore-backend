package com.example.bookstorebackend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "UserWithdrawalRequest", description = "사용자 탈퇴 요청 DTO")
public record UserWithdrawalRequestDto(
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Schema(description = "계정 비밀번호", example = "mypassword123")
        String password
) {}