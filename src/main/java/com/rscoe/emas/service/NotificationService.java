package com.rscoe.emas.service;

import com.rscoe.emas.entity.Notification;
import java.util.List;

public interface NotificationService {

    void sendNotification(String email,String title,String message);

    List<Notification> getUserNotifications(String email);

}