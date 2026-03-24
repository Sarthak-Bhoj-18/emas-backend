package com.rscoe.emas.service.impl;

import com.rscoe.emas.dto.request.LoginRequest;
import com.rscoe.emas.dto.request.RegisterRequest;
import com.rscoe.emas.dto.response.AuthResponse;
import com.rscoe.emas.entity.User;
import com.rscoe.emas.repository.UserRepository;
import com.rscoe.emas.security.JwtUtil;
import com.rscoe.emas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token,user.getRole().name());
    }
    @Override
    public void register(RegisterRequest request){

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDepartment(request.getDepartment());
        user.setEmployeeId(request.getEmployeeId());
        user.setRole(request.getRole());
        user.setActive(true);

        userRepository.save(user);
    }
}