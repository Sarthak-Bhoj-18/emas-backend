package com.rscoe.emas.controller;

import com.rscoe.emas.repository.AttendanceRepository;
import com.rscoe.emas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/stats")
    public Map<String,Object> getStats(){

        Map<String,Object> stats = new HashMap<>();

        stats.put("totalEmployees",
                userRepository.count());

        stats.put("todayAttendance",
                attendanceRepository.count());

        return stats;
    }
}