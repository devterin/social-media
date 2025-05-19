package com.devterin.socialmedia.repositories;

import com.devterin.socialmedia.entities.Post;
import com.devterin.socialmedia.entities.Reaction;
import com.devterin.socialmedia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
    Optional<Reaction> findByPostAndUser(Post post, User user);
    int countByPostId(Long postId);
}
