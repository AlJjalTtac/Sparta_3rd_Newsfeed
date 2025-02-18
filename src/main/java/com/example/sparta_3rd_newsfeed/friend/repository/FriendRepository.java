package com.example.sparta_3rd_newsfeed.friend.repository;

import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.friend.entity.Friend;
import com.example.sparta_3rd_newsfeed.friend.entity.FriendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    default Friend findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "친구 요청이 존재하지 않습니다."));
    }
    Optional<Friend> findBySenderAndReceiver(Member sender, Member receiver);

    @EntityGraph(attributePaths = {"sender"})
    Page<Friend> findByReceiverId(Long receiverId, FriendStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"sender"})
    Page<Friend> findByReceiverIdAndName(Long receiverId, FriendStatus status, String name, Pageable pageable);

    @EntityGraph(attributePaths = {"receiver"})
    Page<Friend> findBySenderId(Long senderId, FriendStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"receiver"})
    Page<Friend> findBySenderIdAndName(Long senderId, FriendStatus status, String name, Pageable pageable);

}
