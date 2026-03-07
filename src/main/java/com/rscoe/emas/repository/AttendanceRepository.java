package com.rscoe.emas.repository;

import com.rscoe.emas.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    Optional<Attendance> findByEmployeeIdAndDate(String employeeId, LocalDate date);

    Optional<Attendance> findByScanToken(String scanToken);
    List<Attendance> findByEmployeeIdAndDateBetween(
            String employeeId,
            LocalDate start,
            LocalDate end
    );
    List<Attendance> findByEmployeeIdOrderByDateDesc(String employeeId);
    List<Attendance> findByDateBetween(LocalDate start, LocalDate end);
}