package com.example.projectmanager.service;

import com.example.projectmanager.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(Long userId, String message, String type);

    List<NotificationDTO> getNotificationsByUser(Long userId);

    List<NotificationDTO> getUnreadNotifications(Long userId);

    void markAsRead(Long notificationId);
}
