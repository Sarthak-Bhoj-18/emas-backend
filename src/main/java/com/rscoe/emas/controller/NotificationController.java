package com.rscoe.emas.controller;

import com.rscoe.emas.entity.Notification;
import com.rscoe.emas.security.JwtUtil;
import com.rscoe.emas.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/my")
    public List<Notification> getMyNotifications(
            HttpServletRequest request){

        String token = request.getHeader("Authorization")
                .substring(7);

        String email = jwtUtil.extractEmail(token);

        return notificationService.getUserNotifications(email);
    }
}