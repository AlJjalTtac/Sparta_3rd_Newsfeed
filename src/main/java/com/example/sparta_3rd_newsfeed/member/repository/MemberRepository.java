package com.example.sparta_3rd_newsfeed.member.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.example.sparta_3rd_newsfeed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndIsDeletedFalse(String email);

    //특정 문자열을 포함하는 회원 조회
    @Query("SELECT m FROM Member m WHERE m.username LIKE %:username%")
    List<Member> findByUsernameLike(@Param("username") String username);

    // findByIdOrElseThrow 메서드 추가
    default Member findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

    }

    List<Member> findByUsernameContaining(String username);

    String username(String username);
}


