package com.example.sparta_3rd_newsfeed.comment.service;

import com.example.sparta_3rd_newsfeed.comment.CommentStatus;
import com.example.sparta_3rd_newsfeed.comment.dto.requestDto.CommentRequestDto;
import com.example.sparta_3rd_newsfeed.comment.dto.responseDto.CommentResponseDto;
import com.example.sparta_3rd_newsfeed.comment.dto.responseDto.SummarizedCommentResponseDto;
import com.example.sparta_3rd_newsfeed.comment.dto.responseDto.SummarizedReplyResponseDto;
import com.example.sparta_3rd_newsfeed.comment.entity.Comment;
import com.example.sparta_3rd_newsfeed.comment.repository.CommentRepository;
import com.example.sparta_3rd_newsfeed.feed.entity.Feed;
import com.example.sparta_3rd_newsfeed.feed.repository.FeedRepository;
import com.example.sparta_3rd_newsfeed.member.entity.Member;
import com.example.sparta_3rd_newsfeed.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;


    // 댓글 조회하고 리스트로 반환
    @Transactional
    public List<SummarizedCommentResponseDto> list(Long feedId){
        List<Comment> comments = commentRepository.findByFeedIdExceptReply(feedId);
        return comments.stream().map(e->{
            List<Comment> replies = commentRepository.findAllReplies(e.getId());
            return new SummarizedCommentResponseDto(e, replies.stream().map(SummarizedReplyResponseDto::new).toList());
        }).collect(Collectors.toList());
    }


    // 댓글 생성
    @Transactional
    public Long create(Long feedId,Long memberId,String content) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다. ID: " + feedId));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + memberId));

        Comment comment = Comment.builder()
                .member(member)
                .feed(feed)
                .contents(content)
                .build();

        comment = commentRepository.save(comment);
        return comment.getId();
    }

     @Transactional
    public void edit(Long commentId,Long memberId,String contents){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. ID: " + commentId));

        if(!comment.getMember().getId().equals(memberId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"수정 권한이 없습니다.");
        }
        if(comment.getCommentStatus() == CommentStatus.ACTIVE
                || comment.getCommentStatus() == CommentStatus.EDITED) {
            comment.updateContent(contents);
            comment.setCommentStatus(CommentStatus.EDITED);
            commentRepository.save(comment);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 수 없는 상태의 댓글입니다.");
        }
    }

    @Transactional
    public void delete(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. ID: " + commentId));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"댓글을 삭제할 수 없습니다.");
        }

        comment.setCommentStatus(CommentStatus.DELETED);

    }

    // 대댓글 생성
    // 부모댓글 Id로부터 댓글 조회
    public Long createReply(Long commentId, Long memberId, String contents) {
        Comment parentComment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. ID: " + commentId));
        Long feedId = parentComment.getFeed().getId();
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다. ID: " + feedId));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + memberId));

        Comment comment = Comment.builder()
                .member(member)
                .feed(feed)
                .contents(contents)
                .commentStatus(CommentStatus.ACTIVE)
                .parentCommentId(parentComment.getId())
                .build();

        comment = commentRepository.save(comment);
        parentComment.addChildComment(comment);
        return comment.getId();
    }


}