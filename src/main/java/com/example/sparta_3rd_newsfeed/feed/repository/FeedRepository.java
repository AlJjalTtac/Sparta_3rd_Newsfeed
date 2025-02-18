package com.example.sparta_3rd_newsfeed.feed.repository;

import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepo- 를 상속받은 Feed Repo- 인터페이스 생성 / Feed 의 id 타입: Long
public interface FeedRepository extends JpaRepository<Feed, Long> {
}