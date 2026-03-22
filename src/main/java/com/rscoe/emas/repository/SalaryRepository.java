package com.rscoe.emas.repository;

import com.rscoe.emas.entity.SalaryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<SalaryRecord, Long> {

    List<SalaryRecord> findByEmployeeEmail(String employeeEmail);
    Optional<SalaryRecord> findByEmployeeEmailAndMonthAndYear(
            String email,
            int month,
            int year
    );
//    Optional<Object> findByEmployeeEmailAndMonthAndYear(String email, int month, int year);
    @Query(value = """
        SELECT COALESCE(SUM(final_salary), 0)
        FROM salary_records
        WHERE month = :month
        """, nativeQuery = true)
    double totalSalaryByMonth(int month);
}