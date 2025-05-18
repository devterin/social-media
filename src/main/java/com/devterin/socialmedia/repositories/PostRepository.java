package com.devterin.socialmedia.repositories;

import com.devterin.socialmedia.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
