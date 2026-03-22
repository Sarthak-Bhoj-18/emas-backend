package com.rscoe.emas.repository;

import com.rscoe.emas.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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


    @Query(value = "SELECT COUNT(*) FROM attendance WHERE date = CURRENT_DATE", nativeQuery = true)
    long countTodayPresent();

    @Query(value = """
        SELECT COALESCE(SUM(EXTRACT(EPOCH FROM (check_out_time - check_in_time)) / 3600),0)
        FROM attendance
        WHERE date = CURRENT_DATE
        """, nativeQuery = true)
    double totalWorkHoursToday();

    @Query(value = """
        SELECT COALESCE(AVG(EXTRACT(HOUR FROM check_in_time)),0)
        FROM attendance
        WHERE date = CURRENT_DATE
        """, nativeQuery = true)
    double avgCheckInHour();
}