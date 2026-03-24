package com.rscoe.emas.repository;

import com.rscoe.emas.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findAllByOrderByCreatedAtDesc();
}
