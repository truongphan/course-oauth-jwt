package com.springboot;

import com.springboot.model.AuthProvider;
import com.springboot.model.User;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseOauthJwtApplication {

    /*@Autowired
    private UserService userService;*/

    public static void main(String[] args) {
        SpringApplication.run(CourseOauthJwtApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .username("truongphan")
                .password("123456")
                .provider(AuthProvider.facebook)
                .email("vantruong@gmail.com")
                .emailVerified(true)
                .build();
        userService.createUser(user);
    }*/
}
