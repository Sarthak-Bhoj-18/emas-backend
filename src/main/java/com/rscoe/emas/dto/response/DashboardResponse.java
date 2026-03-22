package com.rscoe.emas.dto.response;

public class DashboardResponse {

    private long totalEmployees;
    private long activeEmployees;
    private long todayPresent;
    private long todayAbsent;
    private double totalWorkHoursToday;
    private double avgCheckInHour;
    private double totalSalaryThisMonth;

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
}