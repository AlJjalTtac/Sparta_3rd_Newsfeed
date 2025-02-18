package com.example.sparta_3rd_newsfeed.feed.service;

import com.example.sparta_3rd_newsfeed.feed.dto.FeedLikeCountDto;
import com.example.sparta_3rd_newsfeed.feed.repository.FeedRepository;
import com.example.sparta_3rd_newsfeed.feed.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public FeedLikeCountDto getFeedWithLikeCount(Long feedId) {
        Feed feed = feedRepository.findByIdOrElseThrow(feedId);

        long likeCount = likeRepository.countByFeed(feed);

        return new FeedLikeCountDto(feed, likeCount);
    }
}
