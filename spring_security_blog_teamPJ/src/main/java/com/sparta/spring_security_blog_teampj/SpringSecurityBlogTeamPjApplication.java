package com.sparta.spring_security_blog_teampj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringSecurityBlogTeamPjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityBlogTeamPjApplication.class, args);
    }

}
