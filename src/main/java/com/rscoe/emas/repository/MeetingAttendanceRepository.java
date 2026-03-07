package com.rscoe.emas.repository;

import com.rscoe.emas.entity.MeetingAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingAttendanceRepository extends JpaRepository<MeetingAttendance, Long> {
}