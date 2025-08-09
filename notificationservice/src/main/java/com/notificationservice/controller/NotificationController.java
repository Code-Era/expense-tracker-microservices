package com.notificationservice.controller;

import com.notificationservice.dto.NotificationResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notifications/")
public class NotificationController {


    @GetMapping()
    public String getNotification(@Valid @RequestParam(required = false) String userEmail) {
        return "Notification Service is running";
    }

    @PutMapping("/{id}/read")
    public NotificationResponseDto updateNotification(@PathVariable Long id) {
        return new NotificationResponseDto();
    }
}
