package com.rscoe.emas.service.impl;

import com.rscoe.emas.entity.Notification;
import com.rscoe.emas.repository.NotificationRepository;
import com.rscoe.emas.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void sendNotification(String email,String title,String message){

        Notification notification = new Notification();

        notification.setUserEmail(email);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setReadStatus(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications(String email){

        return notificationRepository.findByUserEmailOrderByCreatedAtDesc(email);

    }
}