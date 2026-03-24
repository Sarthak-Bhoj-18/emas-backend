package com.rscoe.emas.dto.response;

import java.util.List;
import java.util.Map;

public class EmployeeDashboardResponse {

    private String name;
    private String department;
    private String todayStatus;      // "CHECKED_IN", "CHECKED_OUT", "NOT_CHECKED_IN"
    private String checkInTime;
    private String checkOutTime;

    // Monthly summary
    private int monthlyPresent;
    private int monthlyAbsent;
    private double monthlyHours;

    // Salary
    private double currentSalary;
    private double overtimeHours;
    private double overtimePay;

    // Notifications
    private List<Map<String, String>> notifications;

    // Weekly attendance trend for chart
    private List<Map<String, Object>> weeklyTrend;

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTodayStatus() { return todayStatus; }
    public void setTodayStatus(String todayStatus) { this.todayStatus = todayStatus; }

    public String getCheckInTime() { return checkInTime; }
    public void setCheckInTime(String checkInTime) { this.checkInTime = checkInTime; }

    public String getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(String checkOutTime) { this.checkOutTime = checkOutTime; }

    public int getMonthlyPresent() { return monthlyPresent; }
    public void setMonthlyPresent(int monthlyPresent) { this.monthlyPresent = monthlyPresent; }

    public int getMonthlyAbsent() { return monthlyAbsent; }
    public void setMonthlyAbsent(int monthlyAbsent) { this.monthlyAbsent = monthlyAbsent; }

    public double getMonthlyHours() { return monthlyHours; }
    public void setMonthlyHours(double monthlyHours) { this.monthlyHours = monthlyHours; }

    public double getCurrentSalary() { return currentSalary; }
    public void setCurrentSalary(double currentSalary) { this.currentSalary = currentSalary; }

    public double getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(double overtimeHours) { this.overtimeHours = overtimeHours; }

    public double getOvertimePay() { return overtimePay; }
    public void setOvertimePay(double overtimePay) { this.overtimePay = overtimePay; }

    public List<Map<String, String>> getNotifications() { return notifications; }
    public void setNotifications(List<Map<String, String>> notifications) { this.notifications = notifications; }

    public List<Map<String, Object>> getWeeklyTrend() { return weeklyTrend; }
    public void setWeeklyTrend(List<Map<String, Object>> weeklyTrend) { this.weeklyTrend = weeklyTrend; }
}
