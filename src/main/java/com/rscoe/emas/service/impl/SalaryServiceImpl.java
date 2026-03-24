package com.rscoe.emas.service.impl;

import com.rscoe.emas.entity.Attendance;
import com.rscoe.emas.entity.SalaryRecord;
import com.rscoe.emas.entity.User;
import com.rscoe.emas.enums.RoleType;
import com.rscoe.emas.repository.AttendanceRepository;
import com.rscoe.emas.repository.SalaryRepository;
import com.rscoe.emas.repository.UserRepository;
import com.rscoe.emas.service.SalaryService;
import com.rscoe.emas.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String calculateMonthlySalary(int month, int year) {

        List<User> users = userRepository.findAll();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // Fetch all attendance for the month in ONE query
        List<Attendance> monthlyAttendance =
                attendanceRepository.findByAttendanceDateBetween(start, end);

        for (User user : users) {

            if (user.getRole() != RoleType.EMPLOYEE) {
                continue;
            }

            if (salaryRepository
                    .findByEmployeeEmailAndMonthAndYear(
                            user.getEmail(), month, year)
                    .isPresent()) {
                continue;
            }

            double totalHours = 0;

            for (Attendance attendance : monthlyAttendance) {

                if (!attendance.getEmployeeId()
                        .equals(user.getEmployeeId())) {
                    continue;
                }

                if (attendance.getCheckOutTime() == null)
                    continue;

                LocalDateTime in = attendance.getCheckInTime();
                LocalDateTime out = attendance.getCheckOutTime();

                double hours = Duration.between(in, out)
                        .toMinutes() / 60.0;

                totalHours += hours;
            }

            double overtimeHours = 0;
            double normalHours = 8 * 20;

            if (totalHours > normalHours) {
                overtimeHours = totalHours - normalHours;
            }

            double hourlyRate = user.getBaseSalary() / 160;

            double overtimePay = overtimeHours * hourlyRate * 1.5;

            double finalSalary = user.getBaseSalary() + overtimePay;

            SalaryRecord record = new SalaryRecord();

            record.setEmployeeEmail(user.getEmail());
            record.setMonth(month);
            record.setYear(year);
            record.setTotalWorkHours(totalHours);
            record.setOvertimeHours(overtimeHours);
            record.setBaseSalary(user.getBaseSalary());
            record.setOvertimePay(overtimePay);
            record.setFinalSalary(finalSalary);
            record.setGeneratedAt(LocalDateTime.now());

            salaryRepository.save(record);

            notificationService.sendNotification(
                    user.getEmail(),
                    "Salary Generated",
                    "Salary for " + month + "/" + year + " has been generated"
            );
        }

        return "Salary generated successfully";
    }

    @Override
    public List<SalaryRecord> getEmployeeSalary(String email) {
        return salaryRepository.findByEmployeeEmail(email);
    }
}