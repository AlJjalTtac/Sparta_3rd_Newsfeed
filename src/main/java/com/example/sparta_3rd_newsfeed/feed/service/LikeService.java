package com.example.sparta_3rd_newsfeed.feed.service;

import com.example.sparta_3rd_newsfeed.feed.entity.Like;
import com.example.sparta_3rd_newsfeed.feed.repository.FeedRepository;
import com.example.sparta_3rd_newsfeed.feed.repository.LikeRepository;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public String like(Member member, Long feedId) {
        Feed feed = feedRepository.findByIdOrElseThrow(feedId);

        if (feed.getMember().equals(member)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 게시물에 좋아요를 남길 수 없습니다.");
        }

        Optional<Like> like = likeRepository.findByMemberAndFeed(member, feed);

        if (like.isPresent()) {
            likeRepository.delete(like.get());
            return "게시물의 좋아요가 취소되었습니다.";
        } else {
            Like newLike = new Like(member, feed);
            likeRepository.save(newLike);
            return "게시물에 좋아요가 추가되었습니다.";
        }
    }

}
