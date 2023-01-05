package com.sparta.spring_security_blog_teampj.service;

import com.sparta.spring_security_blog_teampj.dto.LoginRequestDto;
import com.sparta.spring_security_blog_teampj.dto.SignupRequestDto;
import com.sparta.spring_security_blog_teampj.entity.User;
import com.sparta.spring_security_blog_teampj.entity.UserRoleEnum;
import com.sparta.spring_security_blog_teampj.exception.CustomException;
import com.sparta.spring_security_blog_teampj.exception.ErrorCode;
import com.sparta.spring_security_blog_teampj.jwt.JwtUtil;
import com.sparta.spring_security_blog_teampj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> userFound = userRepository.findByUsername(username);
        if (userFound.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_USERNAME);
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw  new CustomException(ErrorCode.NOT_MATCH_INFORMATION);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
}