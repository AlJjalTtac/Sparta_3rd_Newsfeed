package com.example.sparta_3rd_newsfeed.feed.entity;

import com.example.sparta_3rd_newsfeed.common.entity.BaseEntity;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "feed_like")
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    public Like(Member member, Feed feed) {
        this.member = member;
        this.feed = feed;
    }
}
