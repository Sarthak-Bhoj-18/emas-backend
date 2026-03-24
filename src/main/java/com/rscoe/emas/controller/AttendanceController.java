package com.rscoe.emas.controller;

import com.rscoe.emas.dto.request.ScanQrRequest;
import com.rscoe.emas.dto.response.AttendanceResponse;
import com.rscoe.emas.entity.Attendance;
import com.rscoe.emas.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/scan")
    public org.springframework.http.ResponseEntity<?> scan(@RequestBody ScanQrRequest request){
        if (request == null || request.getToken() == null || request.getToken().trim().isEmpty()) {
            return org.springframework.http.ResponseEntity.badRequest().body("QR token cannot be null or empty");
        }
        try {
            AttendanceResponse result = attendanceService.processScan(request.getToken());
            return org.springframework.http.ResponseEntity.ok(result);
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/history/{employeeId}")
    public List<AttendanceResponse> getAttendanceHistory(
            @PathVariable String employeeId){

        return attendanceService.getEmployeeAttendance(employeeId);
    }
}