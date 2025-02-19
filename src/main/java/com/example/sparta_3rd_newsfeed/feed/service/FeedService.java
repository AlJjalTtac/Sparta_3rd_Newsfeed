package com.example.sparta_3rd_newsfeed.feed.service;

import com.example.sparta_3rd_newsfeed.feed.dto.FeedLikeCountDto;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedRequestDto;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import com.example.sparta_3rd_newsfeed.feed.repository.FeedRepository;
import com.example.sparta_3rd_newsfeed.feed.repository.LikeRepository;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service                    // Service Layer
@RequiredArgsConstructor    // final 붙은 field -> constructor 자동 생성 (Lombok)
@Transactional  // 모든 서비스 메서드 -> 하나의 트랜젝션으로 동작 (오류시 롤백) / version '3.4.2' = Jakarta
public class FeedService {
    // JpaRepository 상속으로 기본 메서드 findAll(), findById(), save(), delete() 등은 이미 존재

    private final FeedRepository feedRepository;    // 생성자 자동 생성
    private final LikeRepository likeRepository;

    // "R" 전체 게시물 조회
    @Transactional(readOnly = true)
    public List<Feed> getAllFeeds() {
        // 모든 Feed 리스트를 가져와서 List<Feed> 형태로 반환
        return feedRepository.findAll();
    }

    // "R" 단일 게시물 조회
    @Transactional(readOnly = true)
    public Feed getFeedById(Long id) {
        // 인자값을 id로 하여 해당 게시글을 찾고 반환 + 해당 ID의 게시글이 없으면 예외 발생
        return feedRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 피드가 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public FeedLikeCountDto getFeedWithLikeCount(Long feedId) {
        Feed feed = feedRepository.findByIdOrElseThrow(feedId);

        long likeCount = likeRepository.countByFeed(feed);

        return new FeedLikeCountDto(feed, likeCount);
    }

    // "C" 게시물 생성
    @Transactional
    public Feed createFeed(FeedRequestDto request, Member member) {
        Feed feed = new Feed(request.getTitle(), request.getContent(), member);

        // DB에 새로운 게시글 저장 & 게시글 객체 반환
        return feedRepository.save(feed);
    }

    // "U" 특정 게시물 수정
    public Feed updateFeed(Long id, FeedRequestDto request, Member member) {
        // 1. 미리 생성한 메소드로 수정하고자 하는 게시글 가져오기
        Feed feed = getFeedById(id);                    // JPA: Persistence Context-REAL
        // 2. 가져온 게시글의 제목과 내용을 인자로 받아온 게시물 데이터로 변경

        if(!feed.getMember().getEmail().equals(member.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 게시물만 수정할 수 있습니다.");
        }

        feed.setTitle(request.getTitle());      // 제목 - JPA: Dirty Checking -> 트렌젝션 종료시 자동 DB 반영
        feed.setContent(request.getContent());  // 내용 - 위와 동일
        // 3. 변경된 게시글 저장 = 업데이트
        return feed;
    }

    // "D" 특정 게시물 삭제
    public void deleteFeed(Long id, Member member) {
        // 1. 미리 생성한 메소드로 삭제하고자 하는 게시글 가져오기
        Feed feed = getFeedById(id);    // JPA: Persistence Context-REAL

        if(!feed.getMember().getEmail().equals(member.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 게시물만 삭제할 수 있습니다.");
        }

        feedRepository.delete(feed);    // 삭제
    }
}
