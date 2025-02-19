package com.example.sparta_3rd_newsfeed.feed.controller;

import com.example.sparta_3rd_newsfeed.feed.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 게시물 좋아요 추가 및 삭제
    @PostMapping("/{feedId}/like")
    public ResponseEntity<String> addLike(
            @SessionAttribute(name = "LOGIN_MEMBER") Long memberId,
            @PathVariable Long feedId
    ) {
        String m = likeService.like(memberId, feedId);

        return new ResponseEntity<>(m, HttpStatus.OK);
    }

}
