/*-- 데이터베이스 생성
USE newsfeed;

-- 회원 테이블
CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        profile_img_id VARCHAR(255) NULL,
                        name VARCHAR(100) NOT NULL,
                        member_email VARCHAR(255) NOT NULL UNIQUE,
                        member_password VARCHAR(255) NOT NULL,
                        profile_bio TEXT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        isDelete BOOLEAN NOT NULL DEFAULT FALSE
);

-- 게시물 테이블
CREATE TABLE feed (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      member_id BIGINT NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      feed_content TEXT NOT NULL,
                      created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

-- 친구 관계 테이블
CREATE TABLE friend (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        sender_id BIGINT NOT NULL,
                        receiver_id BIGINT NOT NULL,
                        status ENUM('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (sender_id) REFERENCES member(id) ON DELETE CASCADE,
                        FOREIGN KEY (receiver_id) REFERENCES member(id) ON DELETE CASCADE
);*/
