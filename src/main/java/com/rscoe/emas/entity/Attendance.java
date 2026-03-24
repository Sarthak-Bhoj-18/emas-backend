package com.rscoe.emas.entity;

import com.rscoe.emas.enums.AttendanceStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;

    private LocalDate attendanceDate;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    @Column(unique = true)
    private String scanToken;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    private Double totalWorkingHours;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getEmployeeId(){
        return employeeId;
    }

    public void setEmployeeId(String employeeId){
        this.employeeId = employeeId;
    }

    public LocalDate getAttendanceDate(){
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate){
        this.attendanceDate = attendanceDate;
    }

    public LocalDateTime getCheckInTime(){
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime){
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime(){
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime){
        this.checkOutTime = checkOutTime;
    }

    public String getScanToken(){
        return scanToken;
    }

    public void setScanToken(String scanToken){
        this.scanToken = scanToken;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public Double getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(Double totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }
}