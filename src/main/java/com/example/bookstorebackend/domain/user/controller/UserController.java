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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserBaseResponseDto>> signUp(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        UserBaseResponseDto baseResponseDto = userService.signUp(userCreateRequestDto);
        return ApiResponse.onSuccess(SuccessCode.CREATE_USER_SUCCESS, baseResponseDto);
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserUpdateResponseDto>> updateMe(
            @AuthenticationPrincipal CustomUserPrincipal principal, @Valid @RequestBody UserUpdateRequestDto req) {
        UserUpdateResponseDto userUpdateResponseDto = userService.updateUser(principal.getUserId(), req);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_USER_SUCCESS, userUpdateResponseDto);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getMe(@AuthenticationPrincipal CustomUserPrincipal principal) {
        UserResponseDto userResponseDto = userService.getUser(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.GET_USER_INFO_SUCCESS, userResponseDto);
    }

    @DeleteMapping("/me/soft-delete")
    public ResponseEntity<ApiResponse<Void>> sofedeleteMe(@Valid @RequestBody UserWithdrawalRequestDto req,
                                                          @AuthenticationPrincipal CustomUserPrincipal principal) {
        userService.softDeleteUser(principal.getUserId(), req);
        return ApiResponse.onSuccess(SuccessCode.SOFT_DELETE_USER_SUCCESS, null);
    }

    @DeleteMapping("/me/permanent")
    public ResponseEntity<ApiResponse<Void>> deleteMe(@AuthenticationPrincipal CustomUserPrincipal principal) {
        userService.deleteUserPermanently(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.HARD_DELETE_USER_SUCCESS, null);
    }
}
