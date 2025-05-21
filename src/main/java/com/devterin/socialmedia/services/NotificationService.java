package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.NotifyRequest;
import com.devterin.socialmedia.dtos.response.NotifyResponse;
import com.devterin.socialmedia.entities.Notification;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.mapper.NotifyMapper;
import com.devterin.socialmedia.repositories.NotificationRepository;
import com.devterin.socialmedia.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotifyMapper notifyMapper;
    private final SimpMessagingTemplate messagingTemplate;

//    public void sendNotification(NotifyRequest request) {
//        User sender = userRepository.findById(request.getSenderId())
//                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));
//
//        User receiver = userRepository.findById(request.getReceiverId())
//                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));
//
//        Notification notification = notifyMapper.toEntity(request);
//        notification.setSender(sender);
//        notification.setReceiver(receiver);
//
//        Notification savedNotify = notificationRepository.save(notification);
//        NotifyResponse response = notifyMapper.toDto(savedNotify);
//
//        messagingTemplate.convertAndSendToUser(
//                receiver.getUsername(), "/socket/notification", response
//        );
//    }

    public NotifyResponse sendNotification(NotifyRequest request) {
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        Notification notification = notifyMapper.toEntity(request);
        notification.setSender(sender);
        notification.setReceiver(receiver);

        Notification savedNotify = notificationRepository.save(notification);
        NotifyResponse response = notifyMapper.toDto(savedNotify);

        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(), "/socket/notification", response
        );

        return response;
    }

    public List<NotifyResponse> getAllNotifications(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Notification> page = notificationRepository.findAll(pageable);
        List<Notification> notifications = page.getContent();
        return notifications.stream().map(notifyMapper::toDto).collect(Collectors.toList());
    } // admin

    public long getUnreadCount(Long receiverId) {
        return notificationRepository.countByReceiverIdAndReadFalse(receiverId);
    }

    @Transactional
    public void markAllAsRead(Long receiverId) {
        notificationRepository.markAllAsRead(receiverId);
    }

    public List<NotifyResponse> findTopTenNotifications(Long receiverId) {
        Pageable topTen = PageRequest.of(0, 10);
        List<Notification> notifications = notificationRepository.findTopTenByReceiverId(receiverId, topTen);
        return notifications.stream().map(notifyMapper::toDto).collect(Collectors.toList());
    }

    private User getUserFromPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
