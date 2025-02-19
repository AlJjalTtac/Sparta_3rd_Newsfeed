package com.example.sparta_3rd_newsfeed.comment.repository;

import com.example.sparta_3rd_newsfeed.comment.dto.CommentCountDto;
import com.example.sparta_3rd_newsfeed.comment.entity.Comment;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT NEW com.example.sparta_3rd_newsfeed.comment.dto.CommentCountDto(c.feed.id, count(c)) " +
    "FROM Comment c " +
    "WHERE c.feed.id in :feedIds " +
    "GROUP BY c.feed.id ")
    List<CommentCountDto> countByFeedIds(List<Long> feedIds);

    List<Comment> findByFeed(Feed feed);
}
