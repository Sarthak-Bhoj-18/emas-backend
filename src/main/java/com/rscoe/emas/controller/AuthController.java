package com.rscoe.emas.controller;

import com.rscoe.emas.dto.request.LoginRequest;
import com.rscoe.emas.dto.request.RegisterRequest;
import com.rscoe.emas.dto.response.AuthResponse;
import com.rscoe.emas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){

        return authService.login(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){

        authService.register(request);

        return "User registered successfully";
    }
}