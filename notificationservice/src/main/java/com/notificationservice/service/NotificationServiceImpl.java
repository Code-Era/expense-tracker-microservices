package com.notificationservice.service;

import com.notificationservice.dto.NotificationRequestDto;
import com.notificationservice.entity.NotificationEntity;
import com.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void consume(NotificationRequestDto event) {
        NotificationEntity notification = new NotificationEntity();
        notification.setMessage(event.getMessage());
        notification.setUserEmail(event.getUserEmail());
        notification.setType(event.getType());
        notification.setStatus("SEND");
        notification.setIsRead(false);
       // notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);

        // Optionally mock SMS or Email send
        System.out.println("📩 Sending mock " + event.getType() + " to " + event.getUserEmail());
    }
}
