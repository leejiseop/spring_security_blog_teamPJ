package com.sparta.spring_security_blog_teampj.service;

import com.sparta.spring_security_blog_teampj.dto.CommentRequestDto;
import com.sparta.spring_security_blog_teampj.dto.CommentResponseDto;
import com.sparta.spring_security_blog_teampj.dto.MessageResponseDto;
import com.sparta.spring_security_blog_teampj.entity.*;
import com.sparta.spring_security_blog_teampj.exception.CustomException;
import com.sparta.spring_security_blog_teampj.repository.CommentLikeRepository;
import com.sparta.spring_security_blog_teampj.repository.CommentRepository;
import com.sparta.spring_security_blog_teampj.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.spring_security_blog_teampj.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );

        Comment comment = commentRepository.save(new Comment(post, user, commentRequestDto));

        int likeCount = 0;

        return new CommentResponseDto(comment, likeCount);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto modifyComment(Long id, Long commentsId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );

        Comment comment;

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(commentsId).orElseThrow(
                    () -> new CustomException(NOT_FOUND_COMMENT)
            );

        } else {
            comment = commentRepository.findByIdAndUserId(commentsId, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
        }
        comment.update(commentRequestDto);

        return new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId()));
    }

    // 댓글 삭제
    @Transactional
    public CommentResponseDto deleteComment(Long id, Long commentsId, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );

        Comment comment;

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(commentsId).orElseThrow(
                    () -> new CustomException(NOT_FOUND_COMMENT)
            );

        } else {
            comment = commentRepository.findByIdAndUserId(commentsId, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
        }

        commentRepository.deleteById(commentsId);

        return new CommentResponseDto(comment,commentLikeRepository.countAllByCommentId(comment.getId()));

    }
    @Transactional
    public boolean checkCommentLike(Long commentId, User user) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId, user.getId());
    }

    // 댓글 좋아요
    @Transactional
    public MessageResponseDto commentLike(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        if(!checkCommentLike(commentId, user)) {
            commentLikeRepository.save(new CommentLike(user, comment));
            return new MessageResponseDto("좋아요 완료", HttpStatus.OK.value());
        } else {
            commentLikeRepository.deleteByCommentIdAndUserId(commentId, user.getId());
            return new MessageResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }
}
