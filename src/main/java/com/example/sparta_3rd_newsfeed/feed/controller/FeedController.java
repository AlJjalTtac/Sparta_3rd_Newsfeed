package com.example.sparta_3rd_newsfeed.feed.controller;

import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import com.example.sparta_3rd_newsfeed.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    // 전체 게시글 조회
    @GetMapping
    public ResponseEntity<List<Feed>> getAllFeeds() {
        return ResponseEntity.ok(feedService.getAllFeeds());
    }

    // 단일 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Feed> getFeedById(@PathVariable Long id) {
        return ResponseEntity.ok(feedService.getFeedById(id));
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Feed> createFeed(@RequestBody Feed feed) {
        return ResponseEntity.ok(feedService.createFeed(feed));
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Feed> updateFeed(@PathVariable Long id, @RequestBody Feed updatedFeed) {
        return ResponseEntity.ok(feedService.updateFeed(id, updatedFeed));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable Long id) {
        feedService.deleteFeed(id);
        return ResponseEntity.noContent().build();
    }
}
