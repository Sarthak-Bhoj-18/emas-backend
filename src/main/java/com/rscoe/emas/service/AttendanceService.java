package com.rscoe.emas.service;

import com.rscoe.emas.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    String processScan(String token);
    List<Attendance> getEmployeeAttendance(String employeeId);
}