package com.sparta.spring_security_blog_teampj.security;

import com.sparta.spring_security_blog_teampj.entity.User;
import com.sparta.spring_security_blog_teampj.exception.CustomException;
import com.sparta.spring_security_blog_teampj.exception.ErrorCode;
import com.sparta.spring_security_blog_teampj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));

        return new UserDetailsImpl(user, user.getUsername());
    }
}