package com.sparta.spring_security_blog_teampj.controller;

import com.sparta.spring_security_blog_teampj.dto.CommentRequestDto;
import com.sparta.spring_security_blog_teampj.dto.CommentResponseDto;
import com.sparta.spring_security_blog_teampj.dto.MessageResponseDto;
import com.sparta.spring_security_blog_teampj.security.UserDetailsImpl;
import com.sparta.spring_security_blog_teampj.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/comments/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id,commentRequestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("api/comments/{id}/{commentsId}")
    public CommentResponseDto modifyComment(@PathVariable Long id, @PathVariable Long commentsId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(id, commentsId, commentRequestDto, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{id}/{commentsId")
    public MessageResponseDto deleteComment(@PathVariable Long id, @PathVariable Long commentsId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, commentsId, userDetails.getUser());
    }

}
