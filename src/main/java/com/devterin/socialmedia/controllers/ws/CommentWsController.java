package com.devterin.socialmedia.controllers.ws;

import com.devterin.socialmedia.dtos.request.AddCommentRequest;
import com.devterin.socialmedia.dtos.response.CommentResponse;
import com.devterin.socialmedia.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentWsController {
    private final CommentService commentService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/addComment")
//    @SendTo("/topic/addComment")
    public CommentResponse addComment(@Payload AddCommentRequest request, Principal principal) {
        CommentResponse commentResponse = commentService.addComment(request, principal);
        messagingTemplate.convertAndSend("/topic/addComment" + request.getPostId(), commentResponse);
        return commentResponse;
    }
}
