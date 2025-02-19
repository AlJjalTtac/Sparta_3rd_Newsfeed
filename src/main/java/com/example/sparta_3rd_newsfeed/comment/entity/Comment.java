package com.example.sparta_3rd_newsfeed.comment.entity;

import com.example.sparta_3rd_newsfeed.comment.CommentStatus;
import com.example.sparta_3rd_newsfeed.common.entity.BaseEntity;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
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

    @Setter
    @Column(updatable = false)
    private Long parentCommentId;

    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL,fetch = LAZY)
    private List<Comment> childComments = new ArrayList<>();

    @Lob
    private String contents;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;


    @Builder
    private Comment(@NotNull Feed feed,
                    @NotNull Member member,
                    String contents,
                    CommentStatus commentStatus,
                    Long parentCommentId) {
        this.feed = feed;
        this.member = member;
        this.contents = contents;
        this.commentStatus = commentStatus;
        this.parentCommentId = parentCommentId;
    }

    public void addChildComment(Comment child) {
        child.setParentCommentId(this.getId());
        this.getChildComments().add(child);
    }


    public void updateContent(String content) {
        this.contents = content;
        this.commentStatus = CommentStatus.EDITED;
    }

}