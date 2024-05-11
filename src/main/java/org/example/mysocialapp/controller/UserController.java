package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) throws UserException {
        return userService.findUserById(userId);
    }

    @PutMapping
    public User updateUser(@RequestBody User user, @RequestHeader("Authorization") String token) throws UserException {
        User reqUser = userService.findUserByJwt(token);
        return userService.updateUser(user, reqUser.getId());
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId) throws UserException {
        return userService.deleteUser(userId);
    }

    @PutMapping("/follow/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization") String token, @PathVariable("userId2") Integer userId2) throws UserException {
        User reqUser = userService.findUserByJwt(token);
        return userService.followUser(reqUser.getId(), userId2);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam("query") String query) {
        return userService.searchUsers(query);
    }

    @GetMapping("/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String token) throws UserException {
        return userService.findUserByJwt(token);
    }
}
