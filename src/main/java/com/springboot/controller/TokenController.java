package com.springboot.controller;

import com.springboot.model.LoginResponse;
import com.springboot.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    private JwtService jwtService;

    @GetMapping("/access-token")
    public LoginResponse getAccessToken(HttpServletRequest request) {
        return jwtService.getAccessToken(request);
    }
}
