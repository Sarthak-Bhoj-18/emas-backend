package com.rscoe.emas.dto.response;

import com.rscoe.emas.enums.PolicyApprovalStatus;
import java.time.LocalDateTime;

public class PolicyResponse {

    private Long id;
    private String title;
    private String content;
    private String adminSignature;
    private String createdBy;
    private LocalDateTime createdAt;
    private PolicyApprovalStatus status;
    private LocalDateTime acknowledgedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdminSignature() {
        return adminSignature;
    }

    public void setAdminSignature(String adminSignature) {
        this.adminSignature = adminSignature;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
