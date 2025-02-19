package com.example.sparta_3rd_newsfeed.feed.controller;

import com.example.sparta_3rd_newsfeed.feed.service.LikeService;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
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
            @SessionAttribute(name = "member") Member member,
            @PathVariable Long feedId
    ) {
        String m = likeService.like(member, feedId);

        return new ResponseEntity<>(m, HttpStatus.OK);
    }

}
