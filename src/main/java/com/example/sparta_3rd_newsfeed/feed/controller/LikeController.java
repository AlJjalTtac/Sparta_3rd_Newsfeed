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

    @PostMapping("/{feedId}/like")
    public ResponseEntity<String> addLike(
            @SessionAttribute(name = "LOGIN_MEMBER") Long memberId,
            @PathVariable Long feedId
    ) {
        likeService.addLike(memberId, feedId);

        return new ResponseEntity<>("게시물에 좋아요가 추가되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/{feedId}/like")
    public ResponseEntity<String> deleteLike(
            @SessionAttribute(name = "LOGIN_MEMBER") Long memberId,
            @PathVariable Long feedId
    ) {
        likeService.deleteLike(memberId, feedId);

        return new ResponseEntity<>("게시물의 좋아요가 취소되었습니다.", HttpStatus.OK);
    }

}
