package com.example.bookstorebackend.common.enums;

import com.example.bookstorebackend.common.response.ApiResult;
import org.springframework.http.HttpStatus;

public enum SuccessCode implements BaseCode {

    ;



    // 본 코드
    private final HttpStatus httpStatus;
    private final String message;
    private final ApiResult apiResult;

    SuccessCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.apiResult = ApiResult.builder()
                .success(true)
                .httpStatus(httpStatus)
                .message(message)
                .build();
    }

    @Override
    public ApiResult getReasonHttpStatus() {
        return apiResult;
    }

}