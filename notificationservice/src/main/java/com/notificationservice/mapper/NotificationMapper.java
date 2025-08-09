package com.notificationservice.mapper;





import com.notificationservice.dto.NotificationRequestDto;
import com.notificationservice.dto.NotificationResponseDto;
import com.notificationservice.entity.NotificationEntity;

import java.time.LocalDate;

public class NotificationMapper {

    public static NotificationEntity mapToCategoryEntity(NotificationRequestDto notificationRequestDto) {
        NotificationEntity notification = new NotificationEntity();
        notification.setMessage(notificationRequestDto.getMessage());
        notification.setUserEmail(notificationRequestDto.getUserEmail());
        notification.setType(notificationRequestDto.getType());
        //notification.setStatus(notificationRequestDto.getStatus());
        //notification.setDate(notificationRequestDto.getDate());
       // notification.setCreatedAt(LocalDate.now());
        return notification;
    }

    public static NotificationResponseDto mapToCategoryResponseDto(NotificationEntity notificationEntity) {
        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        notificationResponseDto.setMessage(notificationEntity.getMessage());
        notificationResponseDto.setUserEmail(notificationEntity.getUserEmail());
        notificationResponseDto.setType(notificationEntity.getType());
        //notificationResponseDto.setStatus(notificationEntity.getStatus());

        return notificationResponseDto;
    }
}
