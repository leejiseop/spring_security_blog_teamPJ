package com.sparta.spring_security_blog_teampj.repository;

import com.sparta.spring_security_blog_teampj.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentid, Long userid);

    void deleteByCommentIdAndUserId(Long commentid, Long userid);

    int countAllByCommentId(Long commentid);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
}
