package com.sparta.spring_security_blog_teampj.dto;

import com.sparta.spring_security_blog_teampj.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {


    //필드
    private Long id;
    private String title;
    private String contents;
    private String username;
    private int postLikeCount;
    private boolean postLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    //생성자


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContent();
        this.username = post.getUsername();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostResponseDto(Post post, List<CommentResponseDto> commentList, boolean postLikeCheck){
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContent();
        this.username = post.getUsername();
        this.postLikeCount = post.getPostLikeList().size();
        this.postLikeCheck = postLikeCheck;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = commentList;
    }
}
