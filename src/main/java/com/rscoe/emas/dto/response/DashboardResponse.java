package com.rscoe.emas.dto.response;

import java.util.List;
import java.util.Map;

public class DashboardResponse {

    private long totalEmployees;
    private long activeEmployees;
    private long todayPresent;
    private long todayAbsent;
    private double totalWorkHoursToday;
    private double avgCheckInHour;
    private double totalSalaryThisMonth;

    // Chart data
    private List<Map<String, Object>> weeklyTrend;
    private List<Map<String, Object>> departmentAttendance;
    private List<Map<String, Object>> departmentDistribution;

    // GETTERS & SETTERS

    public long getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(long totalEmployees) { this.totalEmployees = totalEmployees; }

    public long getActiveEmployees() { return activeEmployees; }
    public void setActiveEmployees(long activeEmployees) { this.activeEmployees = activeEmployees; }

    public long getTodayPresent() { return todayPresent; }
    public void setTodayPresent(long todayPresent) { this.todayPresent = todayPresent; }

    public long getTodayAbsent() { return todayAbsent; }
    public void setTodayAbsent(long todayAbsent) { this.todayAbsent = todayAbsent; }

    public double getTotalWorkHoursToday() { return totalWorkHoursToday; }
    public void setTotalWorkHoursToday(double totalWorkHoursToday) { this.totalWorkHoursToday = totalWorkHoursToday; }

    public double getAvgCheckInHour() { return avgCheckInHour; }
    public void setAvgCheckInHour(double avgCheckInHour) { this.avgCheckInHour = avgCheckInHour; }

    public double getTotalSalaryThisMonth() { return totalSalaryThisMonth; }
    public void setTotalSalaryThisMonth(double totalSalaryThisMonth) { this.totalSalaryThisMonth = totalSalaryThisMonth; }

    public List<Map<String, Object>> getWeeklyTrend() { return weeklyTrend; }
    public void setWeeklyTrend(List<Map<String, Object>> weeklyTrend) { this.weeklyTrend = weeklyTrend; }

    public List<Map<String, Object>> getDepartmentAttendance() { return departmentAttendance; }
    public void setDepartmentAttendance(List<Map<String, Object>> departmentAttendance) { this.departmentAttendance = departmentAttendance; }

    public List<Map<String, Object>> getDepartmentDistribution() { return departmentDistribution; }
    public void setDepartmentDistribution(List<Map<String, Object>> departmentDistribution) { this.departmentDistribution = departmentDistribution; }
}