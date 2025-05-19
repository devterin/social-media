package com.devterin.socialmedia.repositories;

import com.devterin.socialmedia.entities.Post;
import com.devterin.socialmedia.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPost(Post post);
}
