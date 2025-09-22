package com.example.bookstorebackend.common.response;

import com.example.bookstorebackend.common.enums.BaseCode;
import com.example.bookstorebackend.common.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
@Schema(name = "ApiResponse", description = "공통 API 응답 래퍼")
public class ApiResponse<T> {

    @Schema(description = "성공 여부", example = "true")
    private final Boolean isSuccess;

    @Schema(description = "응답 메시지", example = "정상적으로 처리되었습니다.")
    private final String message;

    @Schema(description = "에러 코드 (실패 시에만 존재)", example = "USER_NOT_FOUND", nullable = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final ErrorCode errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "응답 데이터 (성공 시에만 존재)")
    private final T payload;

    // 성공 응답
    public static <T> ResponseEntity<ApiResponse<T>> onSuccess(BaseCode code, T payload) {
        ApiResponse<T> response = new ApiResponse<>(true, code.getReasonHttpStatus().getMessage(), null, payload);
        return ResponseEntity.status(code.getReasonHttpStatus().getHttpStatus()).body(response);
    }

    // 실패 응답
    public static <T> ResponseEntity<ApiResponse<T>> onFailure(BaseCode code) {
        ApiResponse<T> response = new ApiResponse<>(false, code.getReasonHttpStatus().getMessage(), (ErrorCode) code, null);
        return ResponseEntity.status(code.getReasonHttpStatus().getHttpStatus()).body(response);
    }
}
