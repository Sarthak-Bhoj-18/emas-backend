package com.rscoe.emas.service;

import com.rscoe.emas.dto.response.DashboardResponse;
import com.rscoe.emas.repository.AttendanceRepository;
import com.rscoe.emas.repository.SalaryRepository;
import com.rscoe.emas.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

        res.setTotalSalaryThisMonth(
                salaryRepository.totalSalaryByMonth(currentMonth)
        );

        return res;
    }
}