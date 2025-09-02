package com.example.bookstorebackend.common.enums;

import com.example.bookstorebackend.common.response.ApiResult;
import org.springframework.http.HttpStatus;

public enum SuccessCode implements BaseCode {

    CREATE_USER_SUCCESS(HttpStatus.CREATED, "유저를 생성했습니다."),
    UPDATE_USER_SUCCESS(HttpStatus.OK, "유저를 수정했습니다."),
    SOFT_DELETE_USER_SUCCESS(HttpStatus.NO_CONTENT, "사용자가 비활성화되었습니다."),
    HARD_DELETE_USER_SUCCESS(HttpStatus.NO_CONTENT, "사용자가 완전히 삭제되었습니다."),
    GET_USER_INFO_SUCCESS(HttpStatus.OK, "내 정보를 조회했습니다."),
    GET_USER_PROFILE_SUCCESS(HttpStatus.OK, "유저 프로필을 조회했습니다.");



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