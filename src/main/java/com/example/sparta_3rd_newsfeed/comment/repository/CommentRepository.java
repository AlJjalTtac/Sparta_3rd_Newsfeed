package com.example.sparta_3rd_newsfeed.comment.repository;


import com.example.sparta_3rd_newsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
