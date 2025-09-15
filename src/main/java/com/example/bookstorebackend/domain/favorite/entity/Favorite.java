package com.example.bookstorebackend.domain.favorite.entity;

import com.example.bookstorebackend.common.entity.BaseEntity;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


//count갱신 이슈때문에 동시성문제 분산락 걸기.

//i/o가 모두 잦아 병목이 생기기 쉬움. 갑자기 몰리면 느려짐
//일단 같은 bookId에 대해 거의 동시에 요청이 오면 중복 삽입/삭제 이런 가능성을 줄여야 함.
//핫키 병목(인기 도서 한 개에 트래픽 집중) 같은 리소스 키에 대한 쓰기 직렬화. ㅊ처리
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_book", columnNames = {"user_id","book_id"}))
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public static Favorite createFromBook(User user, Book book) {
        return Favorite.builder()
                .user(user)
                .book(book)
                .build();
    }

}
