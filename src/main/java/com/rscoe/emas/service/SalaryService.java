package com.rscoe.emas.service;

import com.rscoe.emas.entity.SalaryRecord;
import java.util.List;

public interface SalaryService {

    String calculateMonthlySalary(int month, int year);

    List<SalaryRecord> getEmployeeSalary(String email);

}