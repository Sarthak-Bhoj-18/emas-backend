package com.rscoe.emas.controller;

import com.rscoe.emas.dto.response.EmployeeDashboardResponse;
import com.rscoe.emas.service.EmployeeDashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeDashboardController {

    @Autowired
    private EmployeeDashboardService employeeDashboardService;

    @GetMapping("/dashboard")
    public EmployeeDashboardResponse getMyDashboard(Authentication authentication) {
        String email = authentication.getName();
        return employeeDashboardService.getEmployeeDashboard(email);
    }
}
