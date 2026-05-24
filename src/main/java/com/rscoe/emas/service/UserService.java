package com.rscoe.emas.service;

public interface UserService {

    String generateQr(String email);

    java.util.List<com.rscoe.emas.dto.response.UserResponse> getAllUsers();

}