package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.NotifyRequest;
import com.devterin.socialmedia.dtos.request.ReactionRequest;
import com.devterin.socialmedia.dtos.response.ReactionResponse;
import com.devterin.socialmedia.entities.Post;
import com.devterin.socialmedia.entities.Reaction;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.repositories.PostRepository;
import com.devterin.socialmedia.repositories.ReactionRepository;
import com.devterin.socialmedia.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ReactionResponse addReaction(ReactionRequest request, Principal principal) {
        log.info("Processing reaction from user: {}", principal.getName());
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found: " + request.getPostId()));

        Reaction reaction = reactionRepository.findByPostAndUser(post, user).orElse(new Reaction());
        reaction.setPost(post);
        reaction.setUser(user);
        reaction.setType(request.getType());

        Reaction savedReaction = reactionRepository.save(reaction);
        int totalReactions = reactionRepository.countByPostId(post.getId());

//        NotifyRequest notifyRequest = NotifyRequest.builder()
//                .senderId()
//                .message()
//                .build();

        return ReactionResponse.builder()
                .id(savedReaction.getId())
                .postId(post.getId())
                .username(user.getUsername())
                .type(savedReaction.getType())
                .totalReactions(totalReactions)
                .build();
    }


    public ReactionResponse deleteReaction(Long postId, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));

        Optional<Reaction> optional = reactionRepository.findByPostAndUser(post, user);

        ReactionResponse.ReactionResponseBuilder responseBuilder = ReactionResponse.builder()
                .postId(post.getId())
                .username(user.getUsername());
        optional.ifPresent(reaction -> {
            responseBuilder.id(reaction.getId());
            responseBuilder.type(reaction.getType());
            reactionRepository.delete(reaction);
        });
        int totalReactions = reactionRepository.countByPostId(post.getId());
        responseBuilder.totalReactions(totalReactions);

        return responseBuilder.build();
    }


}
