package com.devterin.socialmedia.services;

import com.devterin.socialmedia.dtos.request.CreatePostRequest;
import com.devterin.socialmedia.dtos.response.PostResponse;
import com.devterin.socialmedia.entities.Post;
import com.devterin.socialmedia.entities.User;
import com.devterin.socialmedia.mapper.PostMapper;
import com.devterin.socialmedia.repositories.PostRepository;
import com.devterin.socialmedia.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final FirebaseService firebaseService;


//    public PostResponse createPost(CreatePostRequest request, List<MultipartFile> files) {
//
//    }
}
