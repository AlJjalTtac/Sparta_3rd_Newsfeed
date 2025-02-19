package com.example.sparta_3rd_newsfeed.comment.service;

import com.example.sparta_3rd_newsfeed.comment.dto.CommentRequestDto;
import com.example.sparta_3rd_newsfeed.comment.dto.CommentResponseDto;
import com.example.sparta_3rd_newsfeed.comment.entity.Comment;
import com.example.sparta_3rd_newsfeed.comment.repository.CommentRepository;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto saveComment(CommentRequestDto dto) {
        Comment comment = new Comment(dto.getContents());
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment.getId(), saveComment.getContents());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseDto> dtos = new ArrayList<>();

        for (Comment comment : comments) {
            dtos.add(new CommentResponseDto(comment.getId(), comment.getContents()));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 댓글을 찾을 수 없습니다"));
        return new CommentResponseDto(comment.getId(), comment.getContents());
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다"));
        comment.updateComment(dto.getContents());
        return new CommentResponseDto(comment.getId(), comment.getContents());
    }

    @Transactional
    public void deleteByComment(Long id) {
        commentRepository.deleteById(id);
    }
}
