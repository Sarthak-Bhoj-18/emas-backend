package com.rscoe.emas.service;

import com.rscoe.emas.dto.response.DashboardResponse;
import com.rscoe.emas.repository.AttendanceRepository;
import com.rscoe.emas.repository.SalaryRepository;
import com.rscoe.emas.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    public DashboardResponse getDashboardData() {

        DashboardResponse res = new DashboardResponse();

        // ── Scalar stats ──
        long totalEmployees = userRepository.countTotalEmployees();
        long activeEmployees = userRepository.countActiveEmployees();
        long todayPresent = attendanceRepository.countTodayPresent();

        res.setTotalEmployees(totalEmployees);
        res.setActiveEmployees(activeEmployees);
        res.setTodayPresent(todayPresent);
        res.setTodayAbsent(activeEmployees - todayPresent);

        res.setTotalWorkHoursToday(attendanceRepository.totalWorkHoursToday());
        res.setAvgCheckInHour(attendanceRepository.avgCheckInHour());

        int currentMonth = LocalDate.now().getMonthValue();
        res.setTotalSalaryThisMonth(salaryRepository.totalSalaryByMonth(currentMonth));

        // ── Weekly Attendance Trend (area chart) ──
        List<Map<String, Object>> weeklyTrend = new ArrayList<>();
        for (Object[] row : attendanceRepository.weeklyAttendanceCounts()) {
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("day", row[0]);        // e.g. "Mon"
            point.put("date", row[1].toString());
            point.put("count", ((Number) row[2]).longValue());
            weeklyTrend.add(point);
        }
        res.setWeeklyTrend(weeklyTrend);

        // ── Department Attendance Today (bar chart) ──
        List<Map<String, Object>> deptAttendance = new ArrayList<>();
        for (Object[] row : attendanceRepository.departmentAttendanceToday()) {
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("department", row[0] != null ? row[0] : "Unknown");
            point.put("present", ((Number) row[1]).longValue());
            deptAttendance.add(point);
        }
        res.setDepartmentAttendance(deptAttendance);

        // ── Department Distribution (pie chart) ──
        List<Map<String, Object>> deptDist = new ArrayList<>();
        for (Object[] row : userRepository.countByDepartment()) {
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("department", row[0] != null ? row[0] : "Unknown");
            point.put("count", ((Number) row[1]).longValue());
            deptDist.add(point);
        }
        res.setDepartmentDistribution(deptDist);

        return res;
    }
}