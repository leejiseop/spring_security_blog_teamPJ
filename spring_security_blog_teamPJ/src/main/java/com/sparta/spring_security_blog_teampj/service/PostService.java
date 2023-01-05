package com.sparta.spring_security_blog_teampj.service;

import com.sparta.spring_security_blog_teampj.dto.CommentResponseDto;
import com.sparta.spring_security_blog_teampj.dto.MessageResponseDto;
import com.sparta.spring_security_blog_teampj.dto.PostRequestDto;
import com.sparta.spring_security_blog_teampj.dto.PostResponseDto;
import com.sparta.spring_security_blog_teampj.entity.*;
import com.sparta.spring_security_blog_teampj.exception.CustomException;
import com.sparta.spring_security_blog_teampj.exception.ErrorCode;
import com.sparta.spring_security_blog_teampj.repository.CommentLikeRepository;
import com.sparta.spring_security_blog_teampj.repository.PostLikeRepository;
import com.sparta.spring_security_blog_teampj.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;
    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
         Post post = postRepository.save(new Post(postRequestDto, user)); // 값이 담긴 POST 담는다.
         return new PostResponseDto(post);  //
    }

    // 전체 게시글 조회
    @Transactional
    public List<PostResponseDto> getListPosts(User user) {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        // 반환은 responseDto 로

        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : postList){
            List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
            for (Comment comment : post.getComments()){
                commentResponseDtos.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
            }
            postResponseDtos.add(new PostResponseDto(post, commentResponseDtos, (checkPostLike(post.getId(), user))));
        }
        return postResponseDtos;
    }


    public boolean checkPostLike(Long postId, User user){
        return postLikeRepository.existsByPostIdAndUserId(postId, user.getId());
    }
    // 지정 게시글 조회
    @Transactional
    public PostResponseDto getpost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 댓글 조회
        List<CommentResponseDto> commentList = new ArrayList<>();

        for(Comment comment : post.getComments()){
            commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
        }
        return new PostResponseDto(post, commentList, (checkPostLike(post.getId(), user)));
    }

    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        // 조건 - 유저인가 ? 관리자인가?
        Post post;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        }else {
            post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION));
        }
        post.update(postRequestDto);

        List<CommentResponseDto> commentList = new ArrayList<>();

        for(Comment comment : post.getComments()){
            commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
        }
        return new PostResponseDto(post, commentList, (checkPostLike(post.getId(), user)));
    }
    @Transactional
    public void deletePost(Long id, User user) {
        Post post;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
        }else {
            post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION));
        }
        postRepository.delete(post);
    }

    @Transactional
    public MessageResponseDto postLike(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_POST));
        if(!checkPostLike(id, user)){
            postLikeRepository.save(new PostLike(post, user));
            return new MessageResponseDto("좋아요", HttpStatus.OK.value());
        }else {
            postLikeRepository.deleteByPostIdAndUserId(id, user.getId());
            return new MessageResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }


}
