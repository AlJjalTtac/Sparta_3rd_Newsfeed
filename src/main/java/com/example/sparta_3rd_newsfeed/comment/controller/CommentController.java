package com.example.sparta_3rd_newsfeed.comment.controller;

import com.example.sparta_3rd_newsfeed.comment.dto.requestDto.CommentRequestDto;
import com.example.sparta_3rd_newsfeed.comment.dto.responseDto.ResponseIdDto;
import com.example.sparta_3rd_newsfeed.comment.dto.responseDto.SummarizedCommentResponseDto;
import com.example.sparta_3rd_newsfeed.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 모든 댓글 조회
    @GetMapping("/{feedId}")
    public List<SummarizedCommentResponseDto> listComments(@PathVariable("feedId") Long feedId) {
        return commentService.list(feedId);
    }

    // 게시물에 댓글 생성
    @PostMapping("/{feedId}/{memberId}")
    public ResponseIdDto createComment(@PathVariable("feedId") Long feedId,
                                       @PathVariable("memberId") Long memberId,
                                       @Valid @RequestBody CommentRequestDto dto) {
        Long result = commentService.create(feedId, memberId, dto.getContents());
        return new ResponseIdDto(result);
    }


    // 댓글 수정
    @PutMapping("/{commentId}/{memberId}")
    public ResponseEntity<ResponseIdDto> updateComment(@PathVariable("commentId") Long commentId,
                                                       @PathVariable("memberId") Long memberId,
                                                       @Valid @RequestBody CommentRequestDto dto) {
        commentService.edit(commentId, memberId, dto.getContents());
        return ResponseEntity.ok(new ResponseIdDto(commentId));
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}/{memberId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId,
                                              @PathVariable("memberId") Long memberId) {
        commentService.delete(commentId, memberId);
        return ResponseEntity.noContent().build();
    }


    // 대댓글 생성
    @PostMapping("/{commentId}/replies/{memberId}")
    public ResponseIdDto createReply(@PathVariable("commentId") Long commentId,
                                     @PathVariable("memberId") Long memberId,
                                     @Valid @RequestBody CommentRequestDto dto) {
        Long result = commentService.createReply(commentId, memberId, dto.getContents());
        return new ResponseIdDto(result);
    }

    @PutMapping("/replies/{replyId}/{memberId}")
    public ResponseEntity<ResponseIdDto> updateReply(@PathVariable("replyId") Long replyId,
                                                       @PathVariable("memberId") Long memberId,
                                                       @Valid @RequestBody CommentRequestDto dto) {
        commentService.edit(replyId, memberId, dto.getContents());
        return ResponseEntity.ok(new ResponseIdDto(replyId));
    }

    @DeleteMapping("/replies/{replyId}/{memberId}")
    public ResponseEntity<Void> deleteReply(@PathVariable("replyId") Long replyId,
                                              @PathVariable("memberId") Long memberId) {
        commentService.delete(replyId, memberId);
        return ResponseEntity.noContent().build();
    }
}
