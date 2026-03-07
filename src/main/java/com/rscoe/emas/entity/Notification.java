package com.rscoe.emas.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String title;

    private String message;

    private boolean readStatus;

    private LocalDateTime createdAt;

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getUserEmail(){ return userEmail; }
    public void setUserEmail(String userEmail){ this.userEmail = userEmail; }

    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }

    public String getMessage(){ return message; }
    public void setMessage(String message){ this.message = message; }

    public boolean isReadStatus(){ return readStatus; }
    public void setReadStatus(boolean readStatus){ this.readStatus = readStatus; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }
}