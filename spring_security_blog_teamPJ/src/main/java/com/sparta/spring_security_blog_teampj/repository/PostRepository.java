package com.sparta.spring_security_blog_teampj.repository;

import com.sparta.spring_security_blog_teampj.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc(); // 전체조회. 생성일자 내림차순
    Optional<Post> findByIdAndUserId(Long id, Long userId); // 특정 게시글 조회
}
