# 📚 Bookstore Backend

도서 관리와 사용자 활동을 지원하는 Spring Boot 기반 백엔드 애플리케이션입니다. 

---

## 📕 주요 기능

### 1. 회원 관리
- 회원 가입 / 로그인 (JWT 기반 인증)

### 2. 도서 관리
- 도서 CRUD

### 3. 리뷰 관리
- 리뷰 CRUD

### 4. 장바구니 관리
- 도서 장바구니 CRUD

### 5. 구매 내역
- 구매 CRUD

### 6. 랭킹 시스템
- 조회수 기반 랭킹 (조회수 증가 API를 따로 둔 동기 처리 방식)
- 구매수 기반 랭킹 (구매 이벤트에 따른 비동기 처리) 

### 7. 찜하기 (위시리스트)
- 도서 찜 / 취소

---
## 📙 ERD 설계
<img width="1272" height="1258" alt="laksdjflasd" src="https://github.com/user-attachments/assets/8e254f5e-9db0-4874-98bf-6e7b809023d4" />



## 📘 Swagger UI, POSTMAN API명세서

- http://localhost:8080/swagger-ui/index.html
- https://documenter.getpostman.com/view/43249619/2sB3HqHJbY
---

## 📗 실행방법

1. 환경 파일 생성 (`src/main/resources/application.properties`)
2. 로컬에 설치된 Redis실행
