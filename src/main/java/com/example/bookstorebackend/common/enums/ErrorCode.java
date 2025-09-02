package com.example.bookstorebackend.common.enums;

import com.example.bookstorebackend.common.response.ApiResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseCode {

    //USER
    USER_NOT_FOUND(org.springframework.http.HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    ACCOUNT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "존재하지 않는 계정입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USER_NOT_SOFT_DELETED(HttpStatus.BAD_REQUEST, "소프트 삭제된 사용자만 완전 삭제할 수 있습니다."),
    TOKEN_VALIDATION_FAILED(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 일치하지 않습니다.");








    private final HttpStatus httpStatus;
    private final String message;
    private final ApiResult apiResult;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.apiResult = ApiResult.builder()
                .success(false)
                .httpStatus(httpStatus)
                .message(message)
                .build();
    }

    @Override
    public ApiResult getReasonHttpStatus() {
        return apiResult;
    }
}