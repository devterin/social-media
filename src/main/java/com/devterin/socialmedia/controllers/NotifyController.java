package com.devterin.socialmedia.controllers;

import com.devterin.socialmedia.dtos.request.NotifyRequest;
import com.devterin.socialmedia.dtos.response.NotifyResponse;
import com.devterin.socialmedia.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")
public class NotifyController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotifyResponse> sendNotification(@RequestBody NotifyRequest request) {
        NotifyResponse notifyResponse = notificationService.sendNotification(request);
        return ResponseEntity.ok(notifyResponse);
    }
}
