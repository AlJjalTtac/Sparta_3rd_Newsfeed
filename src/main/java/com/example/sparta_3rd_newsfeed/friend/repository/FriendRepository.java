package com.example.sparta_3rd_newsfeed.friend.repository;

import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.friend.entity.Friend;
import com.example.sparta_3rd_newsfeed.friend.entity.FriendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    default Friend findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "친구 요청이 존재하지 않습니다."));
    }
    Optional<Friend> findBySenderAndReceiver(Member sender, Member receiver);

    @Query("SELECT f FROM Friend f JOIN FETCH f.sender WHERE f.receiver.id = :receiverId AND f.status = :status")
    Page<Friend> findByReceiverId(Long receiverId, FriendStatus status, Pageable pageable);

    @Query("SELECT f FROM Friend f JOIN FETCH f.sender s WHERE f.receiver.id = :receiverId AND f.status = :status AND s.username LIKE %:name%")
    Page<Friend> findByReceiverIdAndName(Long receiverId, FriendStatus status, String name, Pageable pageable);

    @Query("SELECT f FROM Friend f JOIN FETCH f.receiver WHERE f.sender.id = :senderId AND f.status = :status")
    Page<Friend> findBySenderId(Long senderId, FriendStatus status, Pageable pageable);

    @Query("SELECT f FROM Friend f JOIN FETCH f.receiver r WHERE f.sender.id = :senderId AND f.status = :status AND r.username LIKE %:name%")
    Page<Friend> findBySenderIdAndName(Long senderId, FriendStatus status, String name, Pageable pageable);

}
