package com.example.sparta_3rd_newsfeed.feed.controller;


import com.example.sparta_3rd_newsfeed.feed.dto.FeedPageResponseDto;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedDetailDto;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedRequestDto;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedResponseDto;
import com.example.sparta_3rd_newsfeed.feed.service.FeedService;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<FeedResponseDto> createFeed(
            @SessionAttribute(name = "member") Member member,
            @RequestBody FeedRequestDto request) {
        return ResponseEntity.ok(feedService.createFeed(request, member));
    }

    // 전체 게시글 조회 (댓글 개수 포함)
    @GetMapping
    public ResponseEntity<Page<FeedPageResponseDto>> getAllFeeds(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(feedService.getAllFeeds(page, size));
    }

    // 게시물 단건 조회 시 좋아요 개수와 댓글 목록 포함
    @GetMapping("/{feedId}")
    public ResponseEntity<FeedDetailDto> getFeedWithDetail(
            @PathVariable Long feedId
    ) {
        return new ResponseEntity<>(feedService.getById(feedId), HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<FeedResponseDto> updateFeed(
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
