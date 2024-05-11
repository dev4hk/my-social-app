package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Story;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserService userService;

    public Story createStory(Story story, User user) {
        Story createdStory = Story.builder()
                .caption(story.getCaption())
                .image(story.getImage())
                .user(user)
                .timestamp(LocalDateTime.now())
                .build();
        return storyRepository.save(createdStory);
    }

    public List<Story> findStoryByUserId(Integer userId) {
        userService.findUserById(userId);
        return storyRepository.findByUserId(userId);
    }


}
