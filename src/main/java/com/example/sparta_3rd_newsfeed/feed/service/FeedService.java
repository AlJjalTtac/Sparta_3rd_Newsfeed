package com.example.sparta_3rd_newsfeed.feed.service;

import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import com.example.sparta_3rd_newsfeed.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service                    // Service Layer
@RequiredArgsConstructor    // final 붙은 field -> constructor 자동 생성 (Lombok)
@Transactional  // 모든 서비스 메서드 -> 하나의 트랜젝션으로 동작 (오류시 롤백) / version '3.4.2' = Jakarta
public class FeedService {
    // JpaRepository 상속으로 기본 메서드 findAll(), findById(), save(), delete() 등은 이미 존재

    private final FeedRepository feedRepository;    // 생성자 자동 생성

    // "R" 전체 게시글 조회
    public List<Feed> getAllFeeds() {
        // 모든 Feed 리스트를 가져와서 List<Feed> 형태로 반환
        return feedRepository.findAll();
    }

    // "R" 특정한 (게시글)ID의 게시글만 조회
    public Feed getFeedById(Long id) {
        // 인자값을 id로 하여 해당 게시글을 찾고 반환 + 해당 ID의 게시글이 없으면 예외 발생
        return feedRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 피드가 존재하지 않습니다."));
    }

    // "C" 게시글 생성
    public Feed createFeed(Feed feed) {
        // DB에 새로운 게시글 저장 & 게시글 객체 반환
        return feedRepository.save(feed);
    }

    // "U" 특정 게시글 수정
    public Feed updateFeed(Long id, Feed newFeed) {
        // 1. 미리 생성한 메소드로 수정하고자 하는 게시글 가져오기
        Feed feed = getFeedById(id);                    // JPA: Persistence Context-REAL
        // 2. 가져온 게시글의 제목과 내용을 인자로 받아온 게시물 데이터로 변경
        feed.setFeedTitle(newFeed.getFeedTitle());      // 제목 - JPA: Dirty Checking -> 트렌젝션 종료시 자동 DB 반영
        feed.setFeedContent(newFeed.getFeedContent());  // 내용 - 위와 동일
        // 3. 변경된 게시글 저장 = 업데이트
        return feedRepository.save(feed);               // JPA의 자동감지에 의존하지 않고 예외발생시 확실한 롤백이 되도록 save 호출
                                                        // 다만, 불필요한 DB 트랜젝션이 발생할 수 있으므로 문제가 된다면 수정하기
    }

    // "D" 특정 게시글 삭제
    public void deleteFeed(Long id) {
        // 1. 미리 생성한 메소드로 삭제하고자 하는 게시글 가져오기
        Feed feed = getFeedById(id);    // JPA: Persistence Context-REAL
        feedRepository.delete(feed);    // 삭제
    }
}
