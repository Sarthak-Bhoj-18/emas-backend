package com.rscoe.emas.service;

import com.rscoe.emas.dto.response.EmployeeDashboardResponse;
import com.rscoe.emas.entity.Attendance;
import com.rscoe.emas.entity.Notification;
import com.rscoe.emas.entity.SalaryRecord;
import com.rscoe.emas.entity.User;
import com.rscoe.emas.repository.AttendanceRepository;
import com.rscoe.emas.repository.NotificationRepository;
import com.rscoe.emas.repository.SalaryRepository;
import com.rscoe.emas.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EmployeeDashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public EmployeeDashboardResponse getEmployeeDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        EmployeeDashboardResponse res = new EmployeeDashboardResponse();
        res.setName(user.getName());
        res.setDepartment(user.getDepartment());

        // ── Today's attendance ──
        LocalDate today = LocalDate.now();
        Optional<Attendance> todayAttendance =
                attendanceRepository.findByEmployeeIdAndAttendanceDate(email, today);

        if (todayAttendance.isEmpty()) {
            res.setTodayStatus("NOT_CHECKED_IN");
            res.setCheckInTime(null);
            res.setCheckOutTime(null);
        } else {
            Attendance att = todayAttendance.get();
            if (att.getCheckOutTime() != null) {
                res.setTodayStatus("CHECKED_OUT");
            } else {
                res.setTodayStatus("CHECKED_IN");
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a");
            res.setCheckInTime(att.getCheckInTime() != null ? att.getCheckInTime().format(fmt) : null);
            res.setCheckOutTime(att.getCheckOutTime() != null ? att.getCheckOutTime().format(fmt) : null);
        }

        // ── Monthly attendance summary ──
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());
        List<Attendance> monthlyRecords =
                attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(email, monthStart, monthEnd);

        int present = monthlyRecords.size();
        // Approximate working days (exclude weekends)
        int workingDays = 0;
        for (LocalDate d = monthStart; !d.isAfter(today); d = d.plusDays(1)) {
            if (d.getDayOfWeek().getValue() <= 5) workingDays++;
        }
        res.setMonthlyPresent(present);
        res.setMonthlyAbsent(Math.max(0, workingDays - present));

        double totalHours = 0;
        for (Attendance att : monthlyRecords) {
            if (att.getCheckInTime() != null && att.getCheckOutTime() != null) {
                totalHours += Duration.between(att.getCheckInTime(), att.getCheckOutTime()).toMinutes() / 60.0;
            }
        }
        res.setMonthlyHours(Math.round(totalHours * 10.0) / 10.0);

        // ── Salary ──
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();
        Optional<SalaryRecord> salary =
                salaryRepository.findByEmployeeEmailAndMonthAndYear(email, currentMonth, currentYear);
        if (salary.isPresent()) {
            SalaryRecord sr = salary.get();
            res.setCurrentSalary(sr.getFinalSalary());
            res.setOvertimeHours(sr.getOvertimeHours());
            res.setOvertimePay(sr.getOvertimePay());
        }

        // ── Recent notifications (last 5) ──
        List<Notification> notifs = notificationRepository.findByUserEmailOrderByCreatedAtDesc(email);
        List<Map<String, String>> notifList = new ArrayList<>();
        int limit = Math.min(notifs.size(), 5);
        for (int i = 0; i < limit; i++) {
            Notification n = notifs.get(i);
            Map<String, String> item = new LinkedHashMap<>();
            item.put("title", n.getTitle());
            item.put("message", n.getMessage());
            item.put("time", n.getCreatedAt() != null ? n.getCreatedAt().toString() : "");
            notifList.add(item);
        }
        res.setNotifications(notifList);

        // ── Weekly attendance trend (last 7 days) ──
        LocalDate weekStart = today.minusDays(6);
        List<Attendance> weekRecords =
                attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(email, weekStart, today);
        Set<LocalDate> presentDates = new HashSet<>();
        for (Attendance a : weekRecords) presentDates.add(a.getAttendanceDate());

        List<Map<String, Object>> weeklyTrend = new ArrayList<>();
        DateTimeFormatter dayFmt = DateTimeFormatter.ofPattern("EEE");
        for (LocalDate d = weekStart; !d.isAfter(today); d = d.plusDays(1)) {
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("day", d.format(dayFmt));
            point.put("present", presentDates.contains(d) ? 1 : 0);
            weeklyTrend.add(point);
        }
        res.setWeeklyTrend(weeklyTrend);

        return res;
    }
}
