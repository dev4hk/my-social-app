package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Story;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.service.StoryService;
import org.example.mysocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/stories")
@RestController
public class StoryController {

    private final StoryService storyService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody Story story, @RequestHeader("Authorization") String token) {
        User reqUser = userService.findUserByJwt(token);
        return new ResponseEntity<>(storyService.createStory(story, reqUser), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Story>> findStoriesByUser(@PathVariable Integer userId, @RequestHeader("Authorization") String token) {
        User reqUser = userService.findUserByJwt(token);
        return new ResponseEntity<>(storyService.findStoryByUserId(userId), HttpStatus.OK);
    }

}
