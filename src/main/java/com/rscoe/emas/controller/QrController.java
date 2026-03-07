package com.rscoe.emas.controller;

import com.rscoe.emas.entity.User;
import com.rscoe.emas.repository.UserRepository;
import com.rscoe.emas.util.QrCodeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/generate/{email}")
    public String generateQr(@PathVariable String email) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = user.getQrToken();

        String content = "ATTENDANCE:" + token;

        String filePath = "qr-" + user.getEmployeeId() + ".png";

        QrCodeUtil.generateQRCode(content,filePath);

        return "QR Generated: " + filePath;
    }
}