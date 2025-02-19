package com.example.sparta_3rd_newsfeed.comment.entity;

import com.example.sparta_3rd_newsfeed.common.entity.BaseEntity;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import com.example.sparta_3rd_newsfeed.member.entity.Member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String contents;

    @Builder
    private Comment(@NotNull Feed feed,
                    @NotNull Member member,
                    String content
    ) {
        this.feed = feed;
        this.member = member;
        this.contents = content;
    }

    public Comment(String contents) {
        this.contents = contents;
    }

    public void updateComment(String contents) {
        this.contents = contents;
    }
}
