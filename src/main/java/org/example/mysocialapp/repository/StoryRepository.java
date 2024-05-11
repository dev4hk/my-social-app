package org.example.mysocialapp.repository;

import org.example.mysocialapp.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    List<Story> findByUserId(Integer userId);

}
