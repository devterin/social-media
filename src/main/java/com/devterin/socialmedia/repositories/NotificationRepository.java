package com.devterin.socialmedia.repositories;

import com.devterin.socialmedia.entities.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.receiver.id = :receiverId ORDER BY n.createdAt DESC")
    List<Notification> findTopTenByReceiverId(@Param("receiverId") Long receiverId, Pageable pageable);

    @Modifying
    @Query("Update Notification n SET n.read = true WHERE n.receiver = :receiver AND n.read = false")
    void markAllAsRead(@Param("receiverId") Long receiver);

    long countByReceiverIdAndReadFalse(Long receiverId);
}
