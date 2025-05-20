package com.devterin.socialmedia.controllers;

import com.devterin.socialmedia.dtos.request.ReactionRequest;
import com.devterin.socialmedia.dtos.response.ReactionResponse;
import com.devterin.socialmedia.services.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReactionWsController {
    private final ReactionService reactionService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/reaction")
    public ReactionResponse processReaction(@Payload ReactionRequest request, Principal principal) {
        ReactionResponse reactionResponse = reactionService.addReaction(request, principal);
        messagingTemplate.convertAndSend("/topic/post/" + request.getPostId() + "/reactions/added", reactionResponse);
        return reactionResponse;
    }


    @MessageMapping("/reaction/delete/{postId}")
    public void deleteReaction(@DestinationVariable Long postId, Principal principal) {
        ReactionResponse reactionResponse = reactionService.deleteReaction(postId, principal);
        messagingTemplate.convertAndSend("/topic/post/" + postId + "/reactions/deleted", reactionResponse);
        System.out.println("Đã xóa reaction: " + reactionResponse);
    }
}
