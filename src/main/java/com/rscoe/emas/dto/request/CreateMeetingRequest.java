package com.rscoe.emas.dto.request;

import java.time.LocalDateTime;

public class CreateMeetingRequest {

    private String title;
    private String description;
    private LocalDateTime meetingTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(LocalDateTime meetingTime) {
        this.meetingTime = meetingTime;
    }
}