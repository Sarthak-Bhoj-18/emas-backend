package com.rscoe.emas.repository;

import com.rscoe.emas.entity.QrSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QrSessionRepository extends JpaRepository<QrSession, Long> {

    Optional<QrSession> findTopByOrderByCreatedAtDesc();
}