package com.rscoe.emas.service;

import com.rscoe.emas.dto.response.AttendanceResponse;
import com.rscoe.emas.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    AttendanceResponse processScan(String token);
    List<AttendanceResponse> getEmployeeAttendance(String employeeId);
}