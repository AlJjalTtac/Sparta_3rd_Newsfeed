package com.example.sparta_3rd_newsfeed.comment.dto.responseDto;

import com.example.sparta_3rd_newsfeed.comment.CommentStatus;
import com.example.sparta_3rd_newsfeed.comment.entity.Comment;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class SummarizedCommentResponseDto {
    private final Long id;

    private final String author;

    private final String content;

    private final List<SummarizedReplyResponseDto> replies;

    public SummarizedCommentResponseDto(Comment comment, List<SummarizedReplyResponseDto> replies) {
        this.id = comment.getId();
        if(checkDeletedComment(comment)) {
            this.author = null;
            this.content = "삭제된 댓글입니다.";
        } else {
            this.author = comment.getMember().getUsername();
            this.content = comment.getContents();
        }
        this.replies = Objects.requireNonNullElseGet(replies, List::of);
    }

    private boolean checkDeletedComment(Comment comment) {
        return comment.getCommentStatus() == CommentStatus.DELETED;
    }
}