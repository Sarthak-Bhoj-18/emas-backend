package com.rscoe.emas.repository;

import com.rscoe.emas.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    Optional<Attendance> findByEmployeeIdAndAttendanceDate(String employeeId, LocalDate attendanceDate);

    Optional<Attendance> findByScanToken(String scanToken);
    List<Attendance> findByEmployeeIdAndAttendanceDateBetween(
            String employeeId,
            LocalDate start,
            LocalDate end
    );
    List<Attendance> findByEmployeeIdOrderByAttendanceDateDesc(String employeeId);
    List<Attendance> findByAttendanceDateBetween(LocalDate start, LocalDate end);


    @Query(value = "SELECT COUNT(*) FROM attendance WHERE attendance_date = CURRENT_DATE", nativeQuery = true)
    long countTodayPresent();

    @Query(value = """
        SELECT COALESCE(SUM(EXTRACT(EPOCH FROM (check_out_time - check_in_time)) / 3600),0)
        FROM attendance
        WHERE attendance_date = CURRENT_DATE
        """, nativeQuery = true)
    double totalWorkHoursToday();

    @Query(value = """
        SELECT COALESCE(AVG(EXTRACT(HOUR FROM check_in_time)),0)
        FROM attendance
        WHERE attendance_date = CURRENT_DATE
        """, nativeQuery = true)
    double avgCheckInHour();

    @Query(value = """
        SELECT TO_CHAR(attendance_date, 'Dy') AS day_label,
               attendance_date,
               COUNT(*) AS cnt
        FROM attendance
        WHERE attendance_date >= CURRENT_DATE - INTERVAL '6 days'
        GROUP BY attendance_date, day_label
        ORDER BY attendance_date
        """, nativeQuery = true)
    List<Object[]> weeklyAttendanceCounts();

    @Query(value = """
        SELECT u.department, COUNT(a.id) AS present
        FROM attendance a
        JOIN users u ON a.employee_id = u.employee_id
        WHERE a.attendance_date = CURRENT_DATE
        GROUP BY u.department
        """, nativeQuery = true)
    List<Object[]> departmentAttendanceToday();
}