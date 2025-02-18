package com.example.sparta_3rd_newsfeed.feed.controller;

import com.example.sparta_3rd_newsfeed.feed.dto.FeedLikeCountDto;
import com.example.sparta_3rd_newsfeed.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    // 게시물 단건 조회 시 좋아요 개수 포함
    @GetMapping("/{feedId}")
    public ResponseEntity<FeedLikeCountDto> getFeedWithLikeCount(
            @PathVariable Long feedId
    ) {
        return new ResponseEntity<>(feedService.getFeedWithLikeCount(feedId), HttpStatus.OK);
    }
}
