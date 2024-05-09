package org.example.mysocialapp.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> followers;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> followings;
}
