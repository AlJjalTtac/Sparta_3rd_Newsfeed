package com.example.sparta_3rd_newsfeed.member.repository;
import org.springframework.stereotype.Repository;

import com.example.sparta_3rd_newsfeed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    
     Optional<Member> findByEmailAndIsDeletedFalse(String email);
}

