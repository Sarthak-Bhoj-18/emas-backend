package com.rscoe.emas.service;

import com.rscoe.emas.dto.request.LoginRequest;
import com.rscoe.emas.dto.request.RegisterRequest;
import com.rscoe.emas.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    void register(RegisterRequest request);

}