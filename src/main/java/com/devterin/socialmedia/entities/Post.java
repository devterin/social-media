package com.devterin.socialmedia.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<PostImage> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Reaction> likes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
