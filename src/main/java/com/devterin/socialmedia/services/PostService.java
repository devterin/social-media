package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.CreatePostRequest;
import com.devterin.socialmedia.dtos.request.UpdatePostRequest;
import com.devterin.socialmedia.dtos.response.PostResponse;
import com.devterin.socialmedia.entities.Post;
import com.devterin.socialmedia.entities.PostImage;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.mapper.PostMapper;
import com.devterin.socialmedia.repositories.PostImageRepository;
import com.devterin.socialmedia.repositories.PostRepository;
import com.devterin.socialmedia.repositories.UserRepository;
import com.devterin.socialmedia.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostMapper postMapper;
    private final FirebaseService firebaseService;

    public PostResponse createPost(CreatePostRequest request, List<MultipartFile> files) {
        User user = getCurrentUser();

        List<PostImage> postImages = new ArrayList<>();

        Post post = postMapper.toEntity(request);
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String imageUrl = firebaseService.uploadFileToFireBase(file, AppConstants.POST);
                    PostImage postImage = PostImage.builder()
                            .imageUrl(imageUrl)
                            .post(savedPost)
                            .build();
                    postImages.add(postImage);
                }
            }
            if (!postImages.isEmpty()) {
                postImageRepository.saveAll(postImages);
                savedPost.setImages(postImages);
                savedPost = postRepository.save(savedPost);
            }
        }
        return postMapper.toDto(savedPost);
    }

    public PostResponse updatePost(UpdatePostRequest request, Long postId) {
        User user = getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UsernameNotFoundException("Post not found"));

        if (user.getUsername().equals(post.getUser().getUsername())) {
            post.setContent(request.getContent());
        }
        return postMapper.toDto(postRepository.save(post));
    }

    @Transactional
    public void deletePost(Long postId) {
        User user = getCurrentUser();
        postRepository.deleteById(postId);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("Post not found: " + postId)
        );
        return postMapper.toDto(post);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("User not authenticated");
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
