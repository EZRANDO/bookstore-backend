package com.example.bookstorebackend.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiResult {

    private final HttpStatus httpStatus;
    private final boolean success;
    private final String message;

}