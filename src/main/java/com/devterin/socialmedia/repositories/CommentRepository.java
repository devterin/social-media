package com.devterin.socialmedia.repositories;

import com.devterin.socialmedia.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
