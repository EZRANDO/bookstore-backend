package com.example.bookstorebackend.domain.user.controller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.user.dto.request.UserCreateRequestDto;
import com.example.bookstorebackend.domain.user.dto.request.UserUpdateRequestDto;
import com.example.bookstorebackend.domain.user.dto.request.UserWithdrawalRequestDto;
import com.example.bookstorebackend.domain.user.dto.response.UserBaseResponseDto;
import com.example.bookstorebackend.domain.user.dto.response.UserResponseDto;
import com.example.bookstorebackend.domain.user.dto.response.UserUpdateResponseDto;
import com.example.bookstorebackend.domain.user.service.UserService;
import com.example.bookstorebackend.security.principal.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserBaseResponseDto>> userSignup(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        UserBaseResponseDto baseResponseDto = userService.userSignup(userCreateRequestDto);
        return ApiResponse.onSuccess(SuccessCode.CREATE_USER_SUCCESS, baseResponseDto);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<ApiResponse<UserBaseResponseDto>> adminSignup(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        UserBaseResponseDto baseResponseDto = userService.adminSignup(userCreateRequestDto);
        return ApiResponse.onSuccess(SuccessCode.CREATE_ADMIN_SUCCESS, baseResponseDto);
    }

    @PatchMapping("users/me")
    public ResponseEntity<ApiResponse<UserUpdateResponseDto>> updateMe(
            @AuthenticationPrincipal CustomUserPrincipal principal, @Valid @RequestBody UserUpdateRequestDto req) {
        UserUpdateResponseDto userUpdateResponseDto = userService.updateUser(principal.getUserId(), req);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_USER_SUCCESS, userUpdateResponseDto);
    }

    @GetMapping("users/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getMe(@AuthenticationPrincipal CustomUserPrincipal principal) {
        UserResponseDto userResponseDto = userService.getUser(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.GET_USER_INFO_SUCCESS, userResponseDto);
    }

    @DeleteMapping("users/me/soft-delete")
    public ResponseEntity<ApiResponse<Void>> sofedeleteMe(@Valid @RequestBody UserWithdrawalRequestDto req,
                                                          @AuthenticationPrincipal CustomUserPrincipal principal) {
        userService.softDeleteUser(principal.getUserId(), req);
        return ApiResponse.onSuccess(SuccessCode.SOFT_DELETE_USER_SUCCESS, null);
    }

    @DeleteMapping("users/me/permanent")
    public ResponseEntity<ApiResponse<Void>> deleteMe(@AuthenticationPrincipal CustomUserPrincipal principal) {
        userService.deleteUserPermanently(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.HARD_DELETE_USER_SUCCESS, null);
    }
}
