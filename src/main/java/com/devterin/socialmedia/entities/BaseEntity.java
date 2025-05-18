package com.devterin.socialmedia.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * @MappedSuperclass
 * Chia sẻ thuộc tính: kế thừa các thuộc tính, phương thức, và ánh xạ từ lớp siêu lớp mà không cần định nghĩa lại.
 * Không tạo bảng riêng: Lớp được chú thích với @MappedSuperclass sẽ không tạo ra một bảng trong cơ sở dữ liệu.
 * Thay vào đó, các thuộc tính của nó sẽ được ánh xạ vào các bảng của các lớp con.
 * */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME(2)")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "DATETIME(2)")
    private LocalDateTime updatedAt;

}