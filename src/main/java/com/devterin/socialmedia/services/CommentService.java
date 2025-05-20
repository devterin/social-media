package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.AddCommentRequest;
import com.devterin.socialmedia.dtos.response.CommentResponse;
import com.devterin.socialmedia.entities.Comment;
import com.devterin.socialmedia.entities.Post;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.repositories.CommentRepository;
import com.devterin.socialmedia.repositories.PostRepository;
import com.devterin.socialmedia.repositories.UserRepository;
import com.devterin.socialmedia.utils.AppConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    public CommentResponse addComment(AddCommentRequest request, Principal principal) {
        String username = principal.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow( () -> new EntityNotFoundException("Post not found"));

        Comment comment = Comment.builder()
                .description(request.getDescription())
                .post(post)
                .user(user)
                .build();
        commentRepository.save(comment);

        var time = LocalDateTime.now();

        return CommentResponse.builder()
                .postId(request.getPostId())
                .description(request.getDescription())
                .username(request.getUsername())
                .avatarUser(user.getAvatar() != null ? user.getAvatar().getImageUrl() : AppConstants.URL_DEFAULT_AVATAR)
                .createdAt(time)
                .build();



    }
}
