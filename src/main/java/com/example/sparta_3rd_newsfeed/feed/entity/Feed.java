package com.example.sparta_3rd_newsfeed.feed.entity;

import com.example.sparta_3rd_newsfeed.common.entity.BaseEntity;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;


@Entity     // Feed 클래스를 JPA의 Entity로 선언
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder    // lambok의 객체생성패턴 사용 : 가독성 & 불변성 보장

public class Feed extends BaseEntity {
    // [id] 게시글 기본 키(ID) - 자동으로 값 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // [feed_title] 게시글 제목 | NOT NULL | 255자 넘기면 잘릴 수도 ?
    @Column(nullable = false)       // JPA Entity - DB 매핑
    private String title;

    // [feed_content] 게시글 내용 | NOT NULL, TEXT 타입(최대 64KB)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    // *** 이 역시 API 작성시엔 그냥 String 으로 설정해서 협의 필요?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Feed (String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

}