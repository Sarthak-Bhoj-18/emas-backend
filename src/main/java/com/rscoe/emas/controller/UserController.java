package com.rscoe.emas.controller;

import com.rscoe.emas.dto.response.QrResponse;
import com.rscoe.emas.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/qr")
    public QrResponse getQr(Authentication authentication){

        String email = authentication.getName();

        String token = userService.generateQr(email);

        return new QrResponse(token);
    }
}