package com.devterin.socialmedia.controllers;

import com.devterin.socialmedia.dtos.request.ReactionRequest;
import com.devterin.socialmedia.dtos.response.ReactionResponse;
import com.devterin.socialmedia.services.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping
    public ResponseEntity<ReactionResponse> addReaction(@RequestBody ReactionRequest request, Principal principal) {
        ReactionResponse response = reactionService.addReaction(request, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}