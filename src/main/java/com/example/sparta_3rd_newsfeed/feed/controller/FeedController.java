package com.example.sparta_3rd_newsfeed.feed.controller;


import com.example.sparta_3rd_newsfeed.feed.dto.FeedLikeCountDto;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedRequestDto;
import com.example.sparta_3rd_newsfeed.feed.service.FeedService;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import org.springframework.web.bind.annotation.*;

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

    // 게시물 단건 조회 시 좋아요 개수 포함
    @GetMapping("/{feedId}")
    public ResponseEntity<FeedLikeCountDto> getFeedWithLikeCount(
            @PathVariable Long feedId
    ) {
        return new ResponseEntity<>(feedService.getFeedWithLikeCount(feedId), HttpStatus.OK);
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Feed> createFeed(
            @SessionAttribute(name = "member") Member member,
            @RequestBody FeedRequestDto request) {
        return ResponseEntity.ok(feedService.createFeed(request, member));
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Feed> updateFeed(
            @SessionAttribute(name = "member") Member member,
            @PathVariable Long id,
            @RequestBody FeedRequestDto request) {
        return ResponseEntity.ok(feedService.updateFeed(id, request, member));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeed(
            @SessionAttribute(name = "member") Member member,
            @PathVariable Long id) {
        feedService.deleteFeed(id, member);
        return ResponseEntity.noContent().build();
    }
}
