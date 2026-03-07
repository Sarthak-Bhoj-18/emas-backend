package com.rscoe.emas.dto.request;

import com.rscoe.emas.enums.RoleType;

public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String department;
    private String employeeId;
    private RoleType role;

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    public String getDepartment(){ return department; }
    public void setDepartment(String department){ this.department = department; }

    public String getEmployeeId(){ return employeeId; }
    public void setEmployeeId(String employeeId){ this.employeeId = employeeId; }

    public RoleType getRole(){ return role; }
    public void setRole(RoleType role){ this.role = role; }
}