package com.devterin.socialmedia.controllers;

import com.devterin.socialmedia.dtos.request.ReactionRequest;
import com.devterin.socialmedia.dtos.response.ReactionResponse;
import com.devterin.socialmedia.services.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class ReactionWebsocketController {
    private final ReactionService reactionService;

    @MessageMapping("/reaction")
//    @SendTo("/topic/reactions")
    public ReactionResponse processReaction(ReactionRequest request) {
        return reactionService.addReaction(request);
    }
}
