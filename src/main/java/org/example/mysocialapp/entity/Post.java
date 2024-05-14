package org.example.mysocialapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private String fileName;

    private String filePath;

    private String contentType;

    @ManyToOne
    private User user;

    @OneToMany
    private Set<User> likedBy = new HashSet<>();

    private LocalDateTime createdAt;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

}
