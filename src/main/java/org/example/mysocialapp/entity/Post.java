package org.example.mysocialapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "posts")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String caption;

    private String image;

    private String video;

    @ManyToOne
    private User user;

    @OneToMany
    private Set<User> likedBy;

    private LocalDateTime createdAt;

}
