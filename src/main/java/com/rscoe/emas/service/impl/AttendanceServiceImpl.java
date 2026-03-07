package com.rscoe.emas.service.impl;

import com.rscoe.emas.entity.Attendance;
import com.rscoe.emas.repository.AttendanceRepository;
import com.rscoe.emas.security.JwtUtil;
import com.rscoe.emas.service.AttendanceService;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public String processScan(String token) {

        Claims claims = jwtUtil.extractClaims(token);

        String type = claims.get("type", String.class);

        if(!"QR".equals(type)){
            throw new RuntimeException("Invalid QR token");
        }

        // replay attack prevention
        Optional<Attendance> tokenUsed = attendanceRepository.findByScanToken(token);

        if(tokenUsed.isPresent()){
            throw new RuntimeException("QR token already used");
        }

        String email = claims.getSubject();

        LocalDate today = LocalDate.now();

        Optional<Attendance> attendanceOpt =
                attendanceRepository.findByEmployeeIdAndDate(email, today);

        if(attendanceOpt.isEmpty()){

            Attendance attendance = new Attendance();

            attendance.setEmployeeId(email);
            attendance.setDate(today);
            attendance.setCheckInTime(LocalDateTime.now());
            attendance.setScanToken(token);

            attendanceRepository.save(attendance);

            return "Check-in recorded";
        }

        Attendance attendance = attendanceOpt.get();

        if(attendance.getCheckOutTime()==null){

            attendance.setCheckOutTime(LocalDateTime.now());
            attendance.setScanToken(token);

            attendanceRepository.save(attendance);

            return "Check-out recorded";
        }

        return "Attendance already completed";
    }
    @Override
    public List<Attendance> getEmployeeAttendance(String employeeId){

        return attendanceRepository
                .findByEmployeeIdOrderByDateDesc(employeeId);
    }
}