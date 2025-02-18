package com.example.sparta_3rd_newsfeed.friend.controller;

import com.example.sparta_3rd_newsfeed.friend.dto.request.StatusUpdateRequestDto;
import com.example.sparta_3rd_newsfeed.friend.dto.response.FriendResponseDto;
import com.example.sparta_3rd_newsfeed.friend.dto.response.PageResponseDto;
import com.example.sparta_3rd_newsfeed.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request")
    public ResponseEntity<String> sendRequest(
            @SessionAttribute(name = "LOGIN_MEMBER") Long memberId,
            @RequestParam Long receiverId
    ) {
        friendService.sendRequest(memberId, receiverId);

        return new ResponseEntity<>("친구 요청이 전송되었습니다", HttpStatus.OK);
    }

    @PutMapping("/request/{friendId}")
    public ResponseEntity<String> updateStatus(
            @SessionAttribute(name = "LOGIN_MEMBER") Long memberId,
            @PathVariable Long friendId,
            @RequestBody StatusUpdateRequestDto request
    ) {
        friendService.updateStatus(memberId, friendId, request);

        return new ResponseEntity<>("친구 요청 상태가 변경되었습니다", HttpStatus.OK);
    }

    @GetMapping("/request/pending")
    public ResponseEntity<PageResponseDto<FriendResponseDto>> getPendingRequests(
            @SessionAttribute(name = "LOGIN_MEMBER") Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponseDto<FriendResponseDto> pendingRequests = friendService.getPendingRequests(memberId, page, size);
        
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/followers")
    public ResponseEntity<PageResponseDto<FriendResponseDto>> getFollowers(
            @RequestParam Long memberId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(friendService.getFollowers(memberId, name, page, size), HttpStatus.OK);
    }

    @GetMapping("/followings")
    public ResponseEntity<PageResponseDto<FriendResponseDto>> getFollowings(
            @RequestParam Long memberId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(friendService.getFollowings(memberId, name, page, size), HttpStatus.OK);
    }

    @DeleteMapping("/{receiverId}")
    public ResponseEntity<String> deleteFriend(
            @SessionAttribute(name = "LOGIN_MEMBER") Long memberId,
            @PathVariable Long receiverId
    ) {
        friendService.delete(memberId, receiverId);

        return new ResponseEntity<>("팔로우가 취소되었습니다.", HttpStatus.OK);
    }
}
