package com.sparta.spring_security_blog_teampj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class MessageResponseDto {

    private String msg;
    private int statusCode;


}