package com.example.sparta_3rd_newsfeed.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    default Feed findByIdOrElseThrow(Long feedId) {
        return findById(feedId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다."));
    }

}
