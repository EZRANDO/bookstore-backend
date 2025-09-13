package com.example.bookstorebackend.domain.user.service;


import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.user.dto.request.UserCreateRequestDto;
import com.example.bookstorebackend.domain.user.dto.request.UserUpdateRequestDto;
import com.example.bookstorebackend.domain.user.dto.request.UserWithdrawalRequestDto;
import com.example.bookstorebackend.domain.user.dto.response.UserBaseResponseDto;
import com.example.bookstorebackend.domain.user.dto.response.UserResponseDto;
import com.example.bookstorebackend.domain.user.dto.response.UserUpdateResponseDto;
import com.example.bookstorebackend.domain.user.entity.User;
import com.example.bookstorebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserBaseResponseDto userSignup(UserCreateRequestDto userCreateRequestDto) {

        //이미 가입된 유저. 소프트 들리트된 유저 전부 확인
        if (userRepository.findByEmail(userCreateRequestDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(userCreateRequestDto.getPassword());

        User user = User.createUserFromSignup(userCreateRequestDto, encodedPassword);

        User saveUser = userRepository.save(user);

        return UserBaseResponseDto.from(saveUser);
    }

    @Transactional
    public UserBaseResponseDto adminSignup(UserCreateRequestDto userCreateRequestDto) {

        //이미 가입된 유저. 소프트 들리트된 유저 전부 확인
        if (userRepository.findByEmail(userCreateRequestDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(userCreateRequestDto.getPassword());

        User user = User.createAdminFromSignup(userCreateRequestDto, encodedPassword);

        User saveUser = userRepository.save(user);

        return UserBaseResponseDto.from(saveUser);
    }


    @Transactional
    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto updateRequestDto) {

        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        //소프트 딜리트 된 유저인지 확인
        if (!user.getEmail().equals(updateRequestDto.getEmail()) &&
                userRepository.existsByEmail(updateRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (updateRequestDto.getPassword() != null && !updateRequestDto.getPassword().isBlank()) {
            String encoded = passwordEncoder.encode(updateRequestDto.getPassword()); // ← 핵심
            user.updateUser(updateRequestDto.getEmail(), encoded);
        } else {
            user.updateUser(updateRequestDto.getEmail(), null); // 이메일만 변경
        }

        return UserUpdateResponseDto.from(user);
    }

    //유저 단건 조회
    public UserResponseDto getUser(Long userId) {

        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.from(user);
    }

    //유저 삭제 (소프트 딜리트)
    @Transactional
    public void softDeleteUser(Long userId, UserWithdrawalRequestDto requestDto) {

        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        user.softDelete();

    }

    //유저 완전히 삭제
    @Transactional
    public void deleteUserPermanently(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.isDeleted()) {
            throw new CustomException(ErrorCode.USER_NOT_SOFT_DELETED);
        }

        userRepository.delete(user);
    }
}

