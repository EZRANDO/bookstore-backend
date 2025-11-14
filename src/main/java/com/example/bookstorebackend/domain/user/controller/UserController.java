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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "사용자 관리 API")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "사용자 회원가입",
            description = "일반 사용자가 회원가입합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "회원가입 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "회원가입 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "유저를 생성했습니다.",
                                      "payload": {
                                        "userId": 1
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<UserBaseResponseDto>> userSignup(
            @Valid @RequestBody UserCreateRequestDto userCreateRequestDto
    ) {
        UserBaseResponseDto baseResponseDto = userService.signUp(userCreateRequestDto);
        return ApiResponse.onSuccess(SuccessCode.CREATE_USER_SUCCESS, baseResponseDto);
    }

    @PatchMapping("/me")
    @Operation(
            summary = "내 정보 수정",
            description = "현재 로그인한 사용자가 자신의 정보를 수정합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "수정 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "내 정보 수정 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "유저를 수정했습니다.",
                                      "payload": {
                                        "userId": 1,
                                         "updatedAt": "2025-09-15T21:56:12.726074"
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<UserUpdateResponseDto>> updateMe(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody UserUpdateRequestDto req
    ) {
        UserUpdateResponseDto userUpdateResponseDto =
                userService.updateUser(principal.getUserId(), req);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_USER_SUCCESS, userUpdateResponseDto);
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 정보 조회",
            description = "현재 로그인한 사용자의 정보를 조회합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "내 정보 조회 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "내 정보를 조회했습니다.",
                                      "payload": {
                                        "userId": 1,
                                        "name": "홍길동",
                                        "email": "hong@test.com"
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<UserResponseDto>> getMe(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        UserResponseDto userResponseDto = userService.getUser(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.GET_USER_INFO_SUCCESS, userResponseDto);
    }

    @DeleteMapping("/me/soft-delete")
    @Operation(
            summary = "소프트 삭제",
            description = "현재 로그인한 사용자를 소프트 삭제(탈퇴 처리)합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "삭제 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "소프트 삭제 성공 예시",
                                    value = """
                                    {
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<Void>> softDeleteMe(
            @Valid @RequestBody UserWithdrawalRequestDto req,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        userService.softDeleteUser(principal.getUserId(), req);
        return ApiResponse.onSuccess(SuccessCode.SOFT_DELETE_USER_SUCCESS, null);
    }

    @DeleteMapping("/me/permanent")
    @Operation(
            summary = "영구 삭제",
            description = "현재 로그인한 사용자를 데이터베이스에서 완전히 삭제합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "삭제 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "영구 삭제 성공 예시",
                                    value = """
                                    {
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<Void>> deleteMe(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        userService.deleteUserPermanently(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.HARD_DELETE_USER_SUCCESS, null);
    }
}