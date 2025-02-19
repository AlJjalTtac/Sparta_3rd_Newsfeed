package com.example.sparta_3rd_newsfeed.comment.dto.responseDto;

import com.example.sparta_3rd_newsfeed.comment.CommentStatus;
import com.example.sparta_3rd_newsfeed.comment.entity.Comment;
import lombok.Getter;

@Getter
public class SummarizedReplyResponseDto {
    private final Long id;

    private final String author;

    private final String content;

    public SummarizedReplyResponseDto(Comment comment) {
        this.id = comment.getId();
        if(checkDeletedReply(comment)) {
            this.author = null;
            this.content = "삭제된 댓글입니다.";
        } else {
            this.author = comment.getMember().getUsername();
            this.content = comment.getContents();
        }
    }

    private boolean checkDeletedReply(Comment comment) {
        return comment.getCommentStatus() == CommentStatus.DELETED;
    }
}
