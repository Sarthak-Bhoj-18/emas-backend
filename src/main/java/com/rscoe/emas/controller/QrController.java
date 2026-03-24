package com.rscoe.emas.controller;

import com.rscoe.emas.entity.Attendance;
import com.rscoe.emas.entity.QrSession;
import com.rscoe.emas.entity.User;
import com.rscoe.emas.repository.AttendanceRepository;
import com.rscoe.emas.repository.QrSessionRepository;
import com.rscoe.emas.repository.UserRepository;
import com.rscoe.emas.service.QrService;
import com.rscoe.emas.util.QrCodeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QrService qrService;

    @Autowired
    private QrSessionRepository qrSessionRepository;

    @GetMapping("/generate/{email}")
    public String generateQr(@PathVariable String email) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = user.getQrToken();

        String content = "ATTENDANCE:" + token;

        String filePath = "qr-" + user.getEmployeeId() + ".png";

        QrCodeUtil.generateQRCode(content,filePath);

        return "QR Generated: " + filePath;
    }

    @GetMapping("/live")
    public String getLiveQr() throws Exception {

        QrSession session = qrService.getLatestQr();

        String content = "ATTENDANCE:" + session.getToken();

        String filePath = "live-qr.png";

        QrCodeUtil.generateQRCode(content, filePath);

        return filePath;
    }

    @PostMapping("/mark")
    public String markAttendance(
            @RequestParam String token,
            @RequestParam String email
    ) {

        // 🔹 1. Validate QR Session
        QrSession session = qrSessionRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new RuntimeException("QR not found"));

        if (!session.getToken().equals(token)) {
            return "Invalid QR";
        }

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "QR Expired";
        }

        // 🔹 2. Get User
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        // 🔹 3. Check if already marked
        Attendance existing = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(user.getEmployeeId(), today)
                .orElse(null);

        if (existing != null) {

            // If check-out not done → mark checkout
            if (existing.getCheckOutTime() == null) {
                existing.setCheckOutTime(LocalDateTime.now());
                attendanceRepository.save(existing);
                return "Check-out marked";
            }

            return "Attendance already completed for today";
        }

        // 🔹 4. Mark Check-in
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(user.getEmployeeId());
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setScanToken(java.util.UUID.randomUUID().toString());

        attendanceRepository.save(attendance);

        return "Check-in marked successfully";
    }

    @PostMapping("/scan")
    public String scanAndMark(@RequestParam String qrData) {

        // 🔹 1. Validate QR format
        if (!qrData.startsWith("EMP:")) {
            return "Invalid QR";
        }

        String employeeId = qrData.substring(4);

        // 🔹 2. Find user
        User user = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        // 🔹 3. Check existing attendance
        Attendance existing = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(employeeId, today)
                .orElse(null);

        if (existing != null) {

            if (existing.getCheckOutTime() == null) {
                existing.setCheckOutTime(LocalDateTime.now());
                attendanceRepository.save(existing);
                return "Check-out marked";
            }

            return "Already completed";
        }

        // 🔹 4. Check-in
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalDateTime.now());

        attendanceRepository.save(attendance);

        return "Check-in marked";
    }

    @GetMapping("/employee/{email}")
    public String generateEmployeeQr(@PathVariable String email) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String content = "EMP:" + user.getEmployeeId();

        String filePath = "emp-" + user.getEmployeeId() + ".png";

        QrCodeUtil.generateQRCode(content, filePath);

        return filePath;
    }
}