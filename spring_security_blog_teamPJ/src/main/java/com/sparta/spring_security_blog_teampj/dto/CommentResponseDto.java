package com.sparta.spring_security_blog_teampj.dto;

import com.sparta.spring_security_blog_teampj.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponseDto {

    private Long id;

    private String username;

    private String comment;

    private int CommentLike;

    public CommentResponseDto(Comment comment, int likeCount) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.CommentLike = likeCount;
    }
}
