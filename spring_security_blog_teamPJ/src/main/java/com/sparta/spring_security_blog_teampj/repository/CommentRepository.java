package com.sparta.spring_security_blog_teampj.repository;

import com.sparta.spring_security_blog_teampj.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long cmtId, Long UserId);
}
