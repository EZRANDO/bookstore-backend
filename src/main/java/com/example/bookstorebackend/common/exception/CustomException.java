package com.example.bookstorebackend.common.exception;

import com.example.bookstorebackend.common.enums.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final BaseCode errorCode;

}
