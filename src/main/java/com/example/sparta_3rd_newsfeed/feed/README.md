# FEED

이 모듈은 `뉴스피드 게시글(피드)에 관련한 기능`을 구현합니다.

## 1. 주요 기능

> 추가 개발에 의해 기능이 추가될 수 있습니다.

### 기능 1. 게시물 작성, 조회, 수정, 삭제

- 게시물에 관한 기본적인 CRUD 구현
- **조건 1.** 게시물 수정, 삭제는 작성자(본인)만 처리할 수 있어야 함
    - **⚠️ 예외처리** 작성자가 아닌 다른 사용자가 게시물 수정, 삭제를 시도하는 경우

### 기능 2. 뉴스피드 조회

- 기본 정렬: 생성일자 기준으로 내림차순 정렬
- 게시글 10개씩 페이지네이션: 각 페이지 당 뉴스피드 데이터 10개

## 2. API 명세

| **Method** | **Endpoint** | **Description** | **Parameters**                                           | **Request Body**                       | **Response**                                                                                       | **Status Code**            |
|------------|--------------|-----------------|----------------------------------------------------------|----------------------------------------|----------------------------------------------------------------------------------------------------|----------------------------|
| `POST`     | `/feed`      | 게시물 생성          | 없음                                                       | `{"title":string, "content": string }` | `{ "id": long, "title":string,"content": string, "createdAt": string, "updatedAt": string }`       | `200 OK`                   |
| `GET`      | `/feed`      | 전체 게시물 조회       | 없음                                                       | 없음                                     | `{ "id": long, "title":string,"content": string,  "createdAt": string, "updatedAt": string, ... }` | `200 OK`                   |
| `GET`      | `/feed/{id}` | 단일 게시물 조회       | **Path:**- `id` (Long)                                   | 없음                                     | `{ "id": long, "title":string,"content": string,  "createdAt": string, "updatedAt": string }`      | `200 OK` / `404 Not Found` |
| `PUT`      | `/feed/{id}` | 게시물 수정          | **Path:**- `id` (Long) , **Session:**-`LOGIN_USER`(Long) | `{"title":string, "content": string}`  | `{ "id": long, "title":string,"content": string,  "createdAt": string, "updatedAt": string }`      | `200 OK` / `404 Not Found` |
| `DELETE`   | `/feed/{id}` | 게시물 삭제          | **Session:**-`LOGIN_USER`(Long)                          | 없음                                     | `{ "message": "Deleted Successfully" }`                                                            | `200 OK` / `404 Not Found` |

## 3. 디렉토리 구조

```
/src/main/java/com/example/sparta_3rd_newsfeed/feed
├── controller/  # 클라이언트 요청 처리 [3 Layered Architecture]
├── dto/         # 데이터 전송 객체 (사용위치: controller - service layer)
├── entity/      # DB 테이블과 1:1 매핑으로 직연결된 데이터 모델 (service - repository)
├── repository/  # 데이터베이스 접근 로직 [3 Layered Architecture]
└── service/     # 비즈니스 로직 [3 Layered Architecture]
```

## 4. ⚠️ 에러 핸들링 가이드

- [(예) 401 (Unauthorized)] : 작성자가 아닌 다른 사용자가 게시물의 수정, 삭제를 시도하는 경우 발생