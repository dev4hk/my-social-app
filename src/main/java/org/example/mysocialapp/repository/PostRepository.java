package org.example.mysocialapp.repository;

import org.example.mysocialapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p from Post p where p.user.id = :userId")
    List<Post> findAllPostsByUserId(@Param("userId") Integer userId);
}
