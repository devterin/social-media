package com.devterin.socialmedia.controllers;

import com.devterin.socialmedia.dtos.request.CreatePostRequest;
import com.devterin.socialmedia.dtos.request.UpdatePostRequest;
import com.devterin.socialmedia.dtos.response.PostResponse;
import com.devterin.socialmedia.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestPart CreatePostRequest request,
                                                   @RequestPart List<MultipartFile> files) {
        var post = postService.createPost(request, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId,
                                                   @RequestBody UpdatePostRequest request) {
        var post = postService.updatePost(request, postId);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        var posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        var post = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
}
