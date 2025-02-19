package com.example.sparta_3rd_newsfeed.feed.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// JpaRepo- 를 상속받은 Feed Repo- 인터페이스 생성 / Feed 의 id 타입: Long
public interface FeedRepository extends JpaRepository<Feed, Long> {

    default Feed findByIdOrElseThrow(Long feedId) {
        return findById(feedId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다."));
    }

    Page<Feed> findByMemberIdIn(List<Long> memberIds, Pageable pageable);

}

