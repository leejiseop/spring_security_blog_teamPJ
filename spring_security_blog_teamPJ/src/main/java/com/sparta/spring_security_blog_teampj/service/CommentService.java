package com.sparta.spring_security_blog_teampj.service;

import com.sparta.spring_security_blog_teampj.dto.CommentRequestDto;
import com.sparta.spring_security_blog_teampj.dto.CommentResponseDto;
import com.sparta.spring_security_blog_teampj.dto.MessageResponseDto;
import com.sparta.spring_security_blog_teampj.entity.Comment;
import com.sparta.spring_security_blog_teampj.entity.CommentLike;
import com.sparta.spring_security_blog_teampj.entity.Post;
import com.sparta.spring_security_blog_teampj.entity.User;
import com.sparta.spring_security_blog_teampj.repository.CommentLikeRepository;
import com.sparta.spring_security_blog_teampj.repository.CommentRepository;
import com.sparta.spring_security_blog_teampj.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );

        Comment comment = commentRepository.save(new Comment(post, user, commentRequestDto));

        int likeCount = 0;

        return new CommentResponseDto(comment, likeCount);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto modifyComment(Long id, Long commentsId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );

        comment.update(commentRequestDto);

        return new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId()));
    }

    // 댓글 삭제
    @Transactional
    public MessageResponseDto deleteComment(Long id, Long commentsId, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );

        Comment comment = commentRepository.findById(commentsId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );

        commentRepository.deleteById(commentsId);

        return new MessageResponseDto();

    }

    public boolean checkCommentLike(Long commentId, User user) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId, user.getId());
    }

    // 댓글 좋아요
    public MessageResponseDto commentLike(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );

        if(!checkCommentLike(commentId, user)) {
            commentLikeRepository.saveAndFlush(new CommentLike(user, comment));
            return new MessageResponseDto("좋아요 완료", HttpStatus.OK.value());
        } else {
            commentLikeRepository.deleteByCommentIdAndUserId(commentId, user.getId());
            return new MessageResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }
}
