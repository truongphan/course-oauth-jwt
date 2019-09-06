package com.springboot;

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
