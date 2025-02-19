package com.example.sparta_3rd_newsfeed.comment.repository;

import com.example.sparta_3rd_newsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "join fetch c.feed " +
            "where c.feed.id = :feedId and c.parentCommentId is null " +
            "order by c.createdAt asc ")
    List<Comment> findByFeedIdExceptReply(@Param("feedId")
                                                  Long feedId);

    @Query("select c from Comment c " +
            "join fetch c.feed " +
            "where c.parentCommentId= :id order by c.createdAt asc ")
    List<Comment> findAllReplies(@Param("id") Long id);
}

