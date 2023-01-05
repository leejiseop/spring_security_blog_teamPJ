package com.sparta.spring_security_blog_teampj.repository;

import com.sparta.spring_security_blog_teampj.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    void deleteByPostIdAndUserId(Long postId, Long userId);
    boolean existsByPostIdAndUserId(Long boardId, Long userId);


}
