package com.sparta.spring_security_blog_teampj.repository;

import com.sparta.spring_security_blog_teampj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
