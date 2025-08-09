package com.notificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponseDto {

    private Long id;
    private String message;
    private String userEmail;
    private String type;
    private boolean readStatus;
}
