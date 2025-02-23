package com.example.sparta_3rd_newsfeed.friend.controller;

import com.example.sparta_3rd_newsfeed.friend.dto.request.StatusUpdateRequestDto;
import com.example.sparta_3rd_newsfeed.friend.dto.response.FriendResponseDto;
import com.example.sparta_3rd_newsfeed.friend.dto.response.PageResponseDto;
import com.example.sparta_3rd_newsfeed.friend.service.FriendService;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
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
            @SessionAttribute(name = "member") Member member,
            @RequestParam Long receiverId
    ) {
        friendService.sendRequest(member, receiverId);

        return new ResponseEntity<>("친구 요청이 전송되었습니다", HttpStatus.OK);
    }

    @PutMapping("/request/{friendId}")
    public ResponseEntity<String> updateStatus(
            @SessionAttribute(name = "member") Member member,
            @PathVariable Long friendId,
            @RequestBody StatusUpdateRequestDto request
    ) {
        friendService.updateStatus(member, friendId, request);

        return new ResponseEntity<>("친구 요청 상태가 변경되었습니다", HttpStatus.OK);
    }

    @GetMapping("/request/pending")
    public ResponseEntity<PageResponseDto<FriendResponseDto>> getPendingRequests(
            @SessionAttribute(name = "member") Member member,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponseDto<FriendResponseDto> pendingRequests = friendService.getPendingRequests(member, page, size);
        
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/{memberId}/followers")
    public ResponseEntity<PageResponseDto<FriendResponseDto>> getFollowers(
            @PathVariable Long memberId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(friendService.getFollowers(memberId, name, page, size), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/followings")
    public ResponseEntity<PageResponseDto<FriendResponseDto>> getFollowings(
            @PathVariable Long memberId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(friendService.getFollowings(memberId, name, page, size), HttpStatus.OK);
    }

    @DeleteMapping("/{receiverId}")
    public ResponseEntity<String> deleteFriend(
            @SessionAttribute(name = "member") Member member,
            @PathVariable Long receiverId
    ) {
        friendService.delete(member, receiverId);

        return new ResponseEntity<>("팔로우가 취소되었습니다.", HttpStatus.OK);
    }
}
