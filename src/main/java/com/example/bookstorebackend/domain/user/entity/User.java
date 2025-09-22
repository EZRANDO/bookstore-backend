package com.example.bookstorebackend.domain.user.entity;

import com.example.bookstorebackend.common.entity.BaseEntity;
import com.example.bookstorebackend.domain.user.dto.request.UserCreateRequestDto;
import com.example.bookstorebackend.domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

//refresh token과 연관관계 매핑을 시켜서 userId에 따라 token을 저장할까 고민.
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    //enum타입을 어떻게 DB에 매핑할지 지정.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static User createUserFromSignup(UserCreateRequestDto userCreateRequestDto, String encodedPassword) {
        return User.builder()
                .email(userCreateRequestDto.getEmail())
                //암호화된 값을 필드에 저장하기 위함.
                .password(encodedPassword)
                .name(userCreateRequestDto.getName())
                .role(Role.USER)
                .deleted(false)
                .build();
    }

    public void updateUser(String newEmail, String newPassword) {
        this.email = newEmail;
        this.password = newPassword;
    }

    public void softDelete() {
        if (!Boolean.TRUE.equals(this.deleted)) {

            this.deleted = true;
            this.deletedAt = LocalDateTime.now();
        }
    }
}

