package com.example.sparta_3rd_newsfeed.feed.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity     // Feed 클래스를 JPA의 Entity로 선언
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder    // lambok의 객체생성패턴 사용 : 가독성 & 불변성 보장
@EntityListeners(AuditingEntityListener.class)  // 생성, 수정날짜 자동기록기능
            // *** Application 에 @EnableJpaAuditing 추가 필요

public class Feed {
    // [id] 게시글 기본 키(ID) - 자동으로 값 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // [feed_title] 게시글 제목 | NOT NULL | 255자 넘기면 잘릴 수도 ?
    @Column(nullable = false)       // JPA Entity - DB 매핑
    private String feedTitle;
    // *** API 작성시 내용이 feed_content 라서 제목도 feed_title이 더 통일감 있을 것 같지만 일단 공동 API로 title 을 적었기때문에 팀원합의 필요

    // [feed_content] 게시글 내용 | NOT NULL, TEXT 타입(최대 64KB)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String feedContent;
    // *** 이 역시 API 작성시엔 그냥 String 으로 설정해서 협의 필요?

    // [created_at] 게시글 생성일 | 자동생성 및 수정불가
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // [updated_at] 게시글 수정일 | 자동생성
    @LastModifiedDate
    private LocalDateTime updatedAt;
}