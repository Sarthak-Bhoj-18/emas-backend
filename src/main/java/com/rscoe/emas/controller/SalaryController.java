package com.rscoe.emas.controller;

import com.rscoe.emas.entity.SalaryRecord;
import com.rscoe.emas.service.SalaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public String generateSalary(@RequestParam int month,
                                 @RequestParam int year) {

        return salaryService.calculateMonthlySalary(month, year);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/my")
    public List<SalaryRecord> mySalary(Authentication authentication) {

        String email = authentication.getName();

        return salaryService.getEmployeeSalary(email);
    }
}