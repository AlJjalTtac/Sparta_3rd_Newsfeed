/*
package com.example.sparta_3rd_newsfeed.friend.repository;

import com.example.sparta_3rd_newsfeed.Member;
import com.example.sparta_3rd_newsfeed.friend.entity.Friend;
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

    @Query("SELECT f FROM Friend f JOIN FETCH f.sender JOIN FETCH f.receiver WHERE f.receiver.id = :receiverId AND f.status = 'ACCEPTED'")
    Page<Friend> findByReceiver(Long receiverId, Pageable pageable);

    @Query("SELECT f FROM Friend f JOIN FETCH f.sender JOIN FETCH f.receiver WHERE f.receiver.id = :receiverId AND f.status = 'ACCEPTED' AND f.sender.memberName LIKE %:name%")
    Page<Friend> findByReceiverAndName(Long receiverId, String name, Pageable pageable);

    @Query("SELECT f FROM Friend f JOIN FETCH f.sender JOIN FETCH f.receiver WHERE f.sender.id = :senderId AND f.status = 'ACCEPTED'")
    Page<Friend> findBySender(Long senderId, Pageable pageable);

    @Query("SELECT f FROM Friend f JOIN FETCH f.sender JOIN FETCH f.receiver WHERE f.sender.id = :senderId AND f.status = 'ACCEPTED' AND f.receiver.memberName LIKE %:name%")
    Page<Friend> findBySenderAndName(Long senderId, String name, Pageable pageable);

    @Query("SELECT f FROM Friend f JOIN FETCH f.sender JOIN FETCH f.receiver WHERE f.receiver.id = :memberId AND f.status = 'PENDING'")
    Page<Friend> findPendingRequests(Long memberId, Pageable pageable);

}
*/
