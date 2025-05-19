package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.ReactionRequest;
import com.devterin.socialmedia.dtos.response.ReactionResponse;
import com.devterin.socialmedia.entities.Post;
import com.devterin.socialmedia.entities.Reaction;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.repositories.PostRepository;
import com.devterin.socialmedia.repositories.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final SimpMessagingTemplate messagingTemplate;

    public ReactionResponse addReaction(ReactionRequest request) {
        User currentUser = postService.getCurrentUser();
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found: " + request.getPostId()));

        // Check if user already reacted to this post
        Reaction reaction = reactionRepository.findByPostAndUser(post, currentUser)
                .orElse(new Reaction());

        reaction.setPost(post);
        reaction.setUser(currentUser);
        reaction.setType(request.getType());

        Reaction savedReaction = reactionRepository.save(reaction);
        int totalReactions = reactionRepository.countByPostId(post.getId());

        ReactionResponse response = ReactionResponse.builder()
                .id(savedReaction.getId())
                .postId(post.getId())
                .username(currentUser.getUsername())
                .type(savedReaction.getType())
                .totalReactions(totalReactions)
                .build();

        // Send WebSocket notification
        messagingTemplate.convertAndSend("/topic/post/" + post.getId() + "/reactions", response);

        return response;
    }


}
