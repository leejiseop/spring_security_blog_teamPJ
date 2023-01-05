package com.sparta.spring_security_blog_teampj.controller;

import com.sparta.spring_security_blog_teampj.dto.MessageResponseDto;
import com.sparta.spring_security_blog_teampj.dto.PostRequestDto;
import com.sparta.spring_security_blog_teampj.dto.PostResponseDto;
import com.sparta.spring_security_blog_teampj.security.UserDetailsImpl;
import com.sparta.spring_security_blog_teampj.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(postService.createPost(postRequestDto, userDetails.getUser()));
    }

    // 전체 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getListPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(postService.getListPosts(userDetails.getUser()));
    }
    // 선택 게시글 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(postService.getpost(id, userDetails.getUser()));
    }
    // 선택 게시글 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(postService.updatePost(id, postRequestDto, userDetails.getUser()));
    }
    // 선택 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<MessageResponseDto> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.ok(new MessageResponseDto("삭제 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/posts/like/{id}")
    public ResponseEntity<MessageResponseDto> postLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.postLike(id, userDetails.getUser()));
    }
}
