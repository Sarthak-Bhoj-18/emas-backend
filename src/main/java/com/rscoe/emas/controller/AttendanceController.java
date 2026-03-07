package com.rscoe.emas.controller;

import com.rscoe.emas.dto.request.ScanQrRequest;
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

    @PreAuthorize("hasRole('SECURITY_GUARD')")
    @PostMapping("/scan")
    public String scan(@RequestBody ScanQrRequest request){

        return attendanceService.processScan(request.getToken());
    }
    @GetMapping("/history/{employeeId}")
    public List<Attendance> getAttendanceHistory(
            @PathVariable String employeeId){

        return attendanceService.getEmployeeAttendance(employeeId);
    }
}