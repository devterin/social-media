package com.devterin.socialmedia.entities;

import com.devterin.socialmedia.enums.RelationshipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "relationship")
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", referencedColumnName = "user_id")
    private User userOne;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", referencedColumnName = "user_id")
    private User userTwo;

    @Enumerated(EnumType.STRING)
    private RelationshipType status;

}
