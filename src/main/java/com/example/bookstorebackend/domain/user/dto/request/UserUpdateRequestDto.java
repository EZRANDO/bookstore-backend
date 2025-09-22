package com.example.bookstorebackend.domain.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "UserUpdateRequest", description = "사용자 정보 수정 요청 DTO")
public class UserUpdateRequestDto {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    @Schema(description = "수정할 이메일", example = "newemail@test.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    @Schema(description = "새 비밀번호", example = "newpassword123", minLength = 4)
    private String password;
}
