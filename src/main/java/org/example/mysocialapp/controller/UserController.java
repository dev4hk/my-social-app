package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.response.UserResponse;
import org.example.mysocialapp.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getUsers(@RequestHeader("Authorization") String token) throws UserException {
        userService.findUserByJwt(token);
        return userService.findAllUsers().stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build()).toList();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") Integer userId, @RequestHeader("Authorization") String token) throws UserException {
        userService.findUserByJwt(token);
        User user = userService.findUserById(userId);
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }

    @GetMapping("/{userId}/photo")
    public String getUserPhoto(@PathVariable("userId") Integer userId, @RequestHeader("Authorization") String token) throws UserException, SQLException {
        userService.findUserByJwt(token);
        return userService.findUserPhotoById(userId);
    }

//    @PutMapping
//    public User updateUser(@RequestBody User user, @RequestHeader("Authorization") String token) throws UserException {
//        User reqUser = userService.findUserByJwt(token);
//        return userService.updateUser(user, reqUser.getId());
//    }

    @PutMapping
    public UserResponse updateUser(
            @RequestHeader("Authorization") String token,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) MultipartFile photo

    ) throws UserException, SQLException, IOException {
        User reqUser = userService.findUserByJwt(token);
        User created =  userService.updateUser(firstName, lastName, photo, reqUser.getId());
        return UserResponse.builder()
                .id(created.getId())
                .firstName(created.getFirstName())
                .lastName(created.getLastName())
                .email(created.getEmail())
                .gender(created.getGender())
                .build();
    }

    @PutMapping("/photo")
    public String updateUserProfilePhoto(@RequestHeader("Authorization") String token, @RequestParam("photo") MultipartFile photo) throws UserException, SQLException, IOException {
        User reqUser = userService.findUserByJwt(token);
        return userService.updateUserProfilePhoto(reqUser.getId(), photo);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId, @RequestHeader("Authorization") String token) throws UserException {
        userService.findUserByJwt(token);
        return userService.deleteUser(userId);
    }

    @PutMapping("/follow/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization") String token, @PathVariable("userId2") Integer userId2) throws UserException {
        User reqUser = userService.findUserByJwt(token);
        return userService.followUser(reqUser.getId(), userId2);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam("query") String query, @RequestHeader("Authorization") String token) throws UserException {
        userService.findUserByJwt(token);
        return userService.searchUsers(query);
    }

    @GetMapping("/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String token) throws UserException {
        return userService.findUserByJwt(token);
    }
}
