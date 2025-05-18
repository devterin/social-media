package com.devterin.socialmedia.entities;

import com.devterin.socialmedia.enums.UserStatus;
import jakarta.persistence.Entity;
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
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private UserImage avatar;

    @OneToOne
    @JoinColumn(name = "background_id")
    private UserImage background;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "userOne", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Relationship> userOneRelationships;

    @OneToMany(mappedBy = "userTwo", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Relationship> userTwoRelationship;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Reaction> reactions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),  // FK User
            inverseJoinColumns = @JoinColumn(name = "role_id") // FK Role
    )
    private Set<Role> roles;
}
