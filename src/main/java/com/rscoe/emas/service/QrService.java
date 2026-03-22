package com.rscoe.emas.service;

import com.rscoe.emas.entity.QrSession;
import com.rscoe.emas.repository.QrSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class QrService {

    @Autowired
    private QrSessionRepository repository;

    public QrSession generateNewQr() {

        String token = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();

        QrSession session = new QrSession();
        session.setToken(token);
        session.setCreatedAt(now);
        session.setExpiresAt(now.plusSeconds(30));

        return repository.save(session);
    }

    public QrSession getLatestQr() {
        return repository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new RuntimeException("No QR found"));
    }
}