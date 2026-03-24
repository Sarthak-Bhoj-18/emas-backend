package com.rscoe.emas.repository;

import com.rscoe.emas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmployeeId(String employeeId);

    java.util.List<User> findByQrToken(String qrToken);

    @Query("SELECT COUNT(u) FROM User u")
    long countTotalEmployees();

    @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
    long countActiveEmployees();

    @Query(value = """
        SELECT department, COUNT(*) AS cnt
        FROM users
        WHERE department IS NOT NULL
        GROUP BY department
        """, nativeQuery = true)
    java.util.List<Object[]> countByDepartment();

}