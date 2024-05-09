package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return userService.findUserById(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable("userId") Integer userId) {
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/follow/{userId1}/{userId2}")
    public User followUserHandler(@PathVariable("userId1") Integer userId1, @PathVariable("userId2") Integer userId2) {
        return userService.followUser(userId1, userId2);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam("query") String query) {
        return userService.searchUsers(query);
    }
}
