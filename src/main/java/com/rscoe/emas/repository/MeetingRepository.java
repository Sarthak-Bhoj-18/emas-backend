package com.rscoe.emas.repository;

import com.rscoe.emas.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findAllByOrderByMeetingTimeDesc();
}