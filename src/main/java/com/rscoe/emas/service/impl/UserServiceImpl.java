
package com.rscoe.emas.service.impl;

import com.rscoe.emas.entity.User;
import com.rscoe.emas.repository.UserRepository;
import com.rscoe.emas.security.JwtUtil;
import com.rscoe.emas.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String generateQr(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getQrToken() == null){

            String token = UUID.randomUUID().toString();

            user.setQrToken(token);

            userRepository.save(user);
        }

        return "ATTENDANCE:" + user.getQrToken();
    }

    @Override
    public java.util.List<com.rscoe.emas.dto.response.UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(u -> new com.rscoe.emas.dto.response.UserResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getDepartment(),
                u.getEmployeeId(),
                u.getRole() != null ? u.getRole().name() : null
        )).toList();
    }
}