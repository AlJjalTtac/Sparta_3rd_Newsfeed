package com.example.sparta_3rd_newsfeed.comment.controller;

import com.example.sparta_3rd_newsfeed.comment.dto.CommentRequestDto;
import com.example.sparta_3rd_newsfeed.comment.dto.CommentResponseDto;
import com.example.sparta_3rd_newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.saveComment(dto));
    }

    @GetMapping("comments")
    public ResponseEntity<List<CommentResponseDto>> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("comments/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.updateComment(id, dto));
    }

    @DeleteMapping("/comment/{id}")
    public void deleteByComment(Long id) {
        commentService.deleteByComment(id);
    }
}
