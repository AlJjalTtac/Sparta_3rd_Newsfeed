package com.example.sparta_3rd_newsfeed.feed.service;

import com.example.sparta_3rd_newsfeed.feed.entity.Like;
import com.example.sparta_3rd_newsfeed.feed.repository.FeedRepository;
import com.example.sparta_3rd_newsfeed.feed.repository.LikeRepository;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final MemberRepository memberRepository;
    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void addLike(Long memberId, Long feedId) {
        Member member = memberRepository.findByIdOrElseThrow(memberId);
        Feed feed = feedRepository.findByIdOrElseThrow(feedId);

        Optional<Like> like = likeRepository.findByMemberAndFeed(member, feed);

        if (like.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 게시물입니다.");
        }

        Like newLike = new Like(member, feed);
        likeRepository.save(newLike);
    }

    @Transactional
    public void deleteLike(Long memberId, Long feedId) {
        Member member = memberRepository.findByIdOrElseThrow(memberId);
        Feed feed = feedRepository.findByIdOrElseThrow(feedId);

        Like findLike = likeRepository.findByMemberAndFeed(member, feed)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요가 존재하지 않습니다."));

        likeRepository.delete(findLike);
    }
}
