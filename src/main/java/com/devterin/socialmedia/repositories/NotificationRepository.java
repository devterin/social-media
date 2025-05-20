package com.devterin.socialmedia.repositories;

import com.devterin.socialmedia.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
