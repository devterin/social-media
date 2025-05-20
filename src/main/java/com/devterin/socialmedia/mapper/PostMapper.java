package com.devterin.socialmedia.mapper;

import com.devterin.socialmedia.dtos.request.CreatePostRequest;
import com.devterin.socialmedia.dtos.response.PostResponse;
import com.devterin.socialmedia.entities.Post;
import com.devterin.socialmedia.entities.PostImage;
import com.devterin.socialmedia.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {
    public Post toEntity(CreatePostRequest request) {
        List<PostImage> images = Optional.ofNullable(request.getImageUrls())
                .orElse(Collections.emptyList())
                .stream().map(url -> PostImage.builder().imageUrl(url).build()).toList();

        return Post.builder()
                .content(request.getContent())
                .images(images)
                .build();
    }

    public PostResponse toDto(Post post) {
        List<String> imageList = Optional.ofNullable(post.getImages())
                .orElse(Collections.emptyList())
                .stream().map(PostImage::getImageUrl).collect(Collectors.toList());

        return PostResponse.builder()
                .postId(post.getId())
                .username(post.getUser().getUsername())
                .content(post.getContent())
                .imageUrls(imageList)
                .commentCount(post.getComments() != null ? post.getComments().size() : 0)
                .likeCount(post.getLikes() != null ? post.getLikes().size() : 0)
                .build();
    }


}
