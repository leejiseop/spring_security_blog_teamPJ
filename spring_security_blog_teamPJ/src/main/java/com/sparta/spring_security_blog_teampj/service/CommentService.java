package com.sparta.spring_security_blog_teampj.service;

import com.sparta.spring_security_blog_teampj.dto.CommentRequestDto;
import com.sparta.spring_security_blog_teampj.dto.CommentResponseDto;
import com.sparta.spring_security_blog_teampj.dto.MessageResponseDto;
import com.sparta.spring_security_blog_teampj.entity.Comment;
import com.sparta.spring_security_blog_teampj.entity.Post;
import com.sparta.spring_security_blog_teampj.entity.User;
import com.sparta.spring_security_blog_teampj.repository.CommentLikeRepository;
import com.sparta.spring_security_blog_teampj.repository.CommentRepository;
import com.sparta.spring_security_blog_teampj.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

        return new CommentResponseDto(comment);
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

        return new CommentResponseDto(comment);
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

    // 댓글 좋아요
}
