package org.example.mysocialapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String gender;

    @Lob
    private Blob photo;

    @ElementCollection
    private Set<Integer> followers = new HashSet<>();

    @ElementCollection
    private Set<Integer> followings = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    private Set<Post> savedPosts = new HashSet<>();

}
