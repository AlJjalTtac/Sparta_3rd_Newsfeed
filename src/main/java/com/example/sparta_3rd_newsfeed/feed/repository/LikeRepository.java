package com.example.sparta_3rd_newsfeed.feed.repository;

import com.example.sparta_3rd_newsfeed.feed.entity.Like;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberAndFeed(Member member, Feed feed);
    long countByFeed(Feed feed);

    @Modifying
    @Query("DELETE FROM Like l WHERE l.feed.id = :feedId")
    void deleteAllByFeedId(@Param("feedId") Long feedId);
}
