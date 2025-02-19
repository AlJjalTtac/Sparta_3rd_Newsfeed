package com.example.sparta_3rd_newsfeed.feed.service;

import com.example.sparta_3rd_newsfeed.comment.dto.CommentCountDto;
import com.example.sparta_3rd_newsfeed.comment.dto.CommentSimpleResponseDto;
import com.example.sparta_3rd_newsfeed.comment.entity.Comment;
import com.example.sparta_3rd_newsfeed.comment.repository.CommentRepository;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedDetailDto;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedPageResponseDto;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedRequestDto;
import com.example.sparta_3rd_newsfeed.feed.dto.FeedResponseDto;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import com.example.sparta_3rd_newsfeed.feed.repository.FeedRepository;
import com.example.sparta_3rd_newsfeed.feed.repository.LikeRepository;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service                    // Service Layer
@RequiredArgsConstructor    // final 붙은 field -> constructor 자동 생성 (Lombok)
@Transactional  // 모든 서비스 메서드 -> 하나의 트랜젝션으로 동작 (오류시 롤백) / version '3.4.2' = Jakarta
public class FeedService {
    // JpaRepository 상속으로 기본 메서드 findAll(), findById(), save(), delete() 등은 이미 존재

    private final FeedRepository feedRepository;    // 생성자 자동 생성
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    // "C" 게시물 생성
    @Transactional
    public FeedResponseDto createFeed(FeedRequestDto request, Member member) {
        Feed feed = new Feed(request.getTitle(), request.getContent(), member);

        // DB에 새로운 게시글 저장 & 게시글 객체 반환
        feedRepository.save(feed);

        return new FeedResponseDto(feed);
    }

    // "R" 전체 게시물 조회
    @Transactional(readOnly = true)
    public Page<FeedPageResponseDto> getAllFeeds(int page, int size) {
        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by(Sort.Order.desc("updatedAt")));
        Page<Feed> feedPage = feedRepository.findAll(pageable);
        List<Long> feedIds = feedPage.stream()
                .map(Feed::getId)
                .collect(Collectors.toList());

        List<CommentCountDto> countResults = commentRepository.countByFeedIds(feedIds);
        Map<Long, Long> commentCountMap = countResults.stream()
                .collect(Collectors.toMap(CommentCountDto::getFeedId, CommentCountDto::getCount));

        return feedPage.map(feed -> new FeedPageResponseDto(
                feed.getId(),
                feed.getTitle(),
                feed.getMember().getEmail(),
                commentCountMap.getOrDefault(feed.getId(), 0L).intValue(),
                feed.getCreatedAt(),
                feed.getUpdatedAt()
        ));
    }

    @Transactional(readOnly = true)
    public FeedDetailDto getById(Long feedId) {
        Feed feed = feedRepository.findByIdOrElseThrow(feedId);

        List<Comment> comments = commentRepository.findByFeed(feed);
        List<CommentSimpleResponseDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentSimpleResponseDto commentSimpleResponseDto = new CommentSimpleResponseDto(
                    comment.getId(), comment.getContents()
            );

            commentDtos.add(commentSimpleResponseDto);
        }
        // 좋아요 개수
        long likeCount = likeRepository.countByFeed(feed);

        return new FeedDetailDto(feed, likeCount, commentDtos);
    }

    // "U" 특정 게시물 수정
    public FeedResponseDto updateFeed(Long id, FeedRequestDto request, Member member) {
        // 1. 미리 생성한 메소드로 수정하고자 하는 게시글 가져오기
        Feed feed = feedRepository.findByIdOrElseThrow(id);                    // JPA: Persistence Context-REAL
        // 2. 가져온 게시글의 제목과 내용을 인자로 받아온 게시물 데이터로 변경

        if(!feed.getMember().getEmail().equals(member.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 게시물만 수정할 수 있습니다.");
        }

        feed.setTitle(request.getTitle());      // 제목 - JPA: Dirty Checking -> 트렌젝션 종료시 자동 DB 반영
        feed.setContent(request.getContent());  // 내용 - 위와 동일
        // 3. 변경된 게시글 저장 = 업데이트
        entityManager.flush();

        return new FeedResponseDto(feed);
    }

    // "D" 특정 게시물 삭제
    public void deleteFeed(Long id, Member member) {
        // 1. 미리 생성한 메소드로 삭제하고자 하는 게시글 가져오기
        Feed feed = feedRepository.findByIdOrElseThrow(id);    // JPA: Persistence Context-REAL

        if(!feed.getMember().getEmail().equals(member.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 게시물만 삭제할 수 있습니다.");
        }

        feedRepository.delete(feed);    // 삭제
    }
}
