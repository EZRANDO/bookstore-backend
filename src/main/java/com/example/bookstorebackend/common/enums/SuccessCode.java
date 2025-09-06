package com.example.bookstorebackend.common.enums;

import com.example.bookstorebackend.common.response.ApiResult;
import org.springframework.http.HttpStatus;

public enum SuccessCode implements BaseCode {

    //USER
    CREATE_USER_SUCCESS(HttpStatus.CREATED, "유저를 생성했습니다."),
    UPDATE_USER_SUCCESS(HttpStatus.OK, "유저를 수정했습니다."),
    SOFT_DELETE_USER_SUCCESS(HttpStatus.NO_CONTENT, "사용자가 비활성화되었습니다."),
    HARD_DELETE_USER_SUCCESS(HttpStatus.NO_CONTENT, "사용자가 완전히 삭제되었습니다."),
    GET_USER_INFO_SUCCESS(HttpStatus.OK, "내 정보를 조회했습니다."),
    GET_USER_PROFILE_SUCCESS(HttpStatus.OK, "유저 프로필을 조회했습니다."),

    //BOOK
    CREATE_BOOK_SUCCESS(HttpStatus.CREATED, "도서가 성공적으로 등록되었습니다."),
    GET_BOOK_SUCCESS(HttpStatus.OK, "도서 조회에 성공했습니다."),
    GET_BOOK_LIST_SUCCESS(HttpStatus.OK, "도서 목록 조회에 성공했습니다."),
    UPDATE_BOOK_SUCCESS(HttpStatus.OK, "도서가 성공적으로 수정되었습니다."),
    DELETE_BOOK_SUCCESS(HttpStatus.NO_CONTENT, "도서가 성공적으로 삭제되었습니다."),

    //CART
    ADD_CART_ITEM_SUCCESS(HttpStatus.CREATED, "장바구니에 도서가 성공적으로 추가되었습니다."),
    UPDATE_CART_ITEM_SUCCESS(HttpStatus.OK, "장바구니 항목이 성공적으로 수정되었습니다."),
    DELETE_CART_ITEM_SUCCESS(HttpStatus.NO_CONTENT, "장바구니 항목이 성공적으로 삭제되었습니다."),
    GET_CART_ITEMS_SUCCESS(HttpStatus.OK, "장바구니 항목 목록이 성공적으로 조회되었습니다."),

    //ORDER
    CREATE_ORDER_SUCCESS(HttpStatus.CREATED, "주문이 정상적으로 생성되었습니다."),
    GET_ORDER_ITEMS_SUCCESS(HttpStatus.OK, "주문 항목 목록을 정상적으로 조회했습니다."),
    UPDATE_ORDER_STATUS_SUCCESS(HttpStatus.OK, "주문 상태를 정상적으로 변경했습니다."),

    //REVIEW
    CREATE_REVIEW_SUCCESS(HttpStatus.CREATED, "리뷰를 생성했습니다."),
    GET_REVIEWS_SUCCESS (HttpStatus.OK,"리뷰 목록을 조회했습니다."),
    GET_REVIEW_SUCCESS  (HttpStatus.OK,"리뷰를 조회했습니다."),
    UPDATE_REVIEW_SUCCESS(HttpStatus.OK,"리뷰를 수정했습니다."),
    DELETE_REVIEW_SUCCESS(HttpStatus.OK,  "리뷰를 삭제했습니다.");


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