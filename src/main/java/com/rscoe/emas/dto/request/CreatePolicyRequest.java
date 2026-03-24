package com.rscoe.emas.dto.request;

public class CreatePolicyRequest {

    private String title;
    private String content;
    private String adminSignature;

    // Getters and Setters
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
}
