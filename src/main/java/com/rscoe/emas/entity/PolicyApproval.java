package com.rscoe.emas.entity;

import com.rscoe.emas.enums.PolicyApprovalStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "policy_approvals")
public class PolicyApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long policyId;

    private String employeeEmail;

    @Enumerated(EnumType.STRING)
    private PolicyApprovalStatus status;

    private LocalDateTime acknowledgedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public PolicyApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyApprovalStatus status) {
        this.status = status;
    }

    public LocalDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
    }
}
