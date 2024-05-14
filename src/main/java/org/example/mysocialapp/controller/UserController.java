package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.response.UserUpdateResponse;
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
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) throws UserException {
        return userService.findUserById(userId);
    }

    @GetMapping("/{userId}/photo")
    public String getUserPhoto(@PathVariable("userId") Integer userId) throws UserException, SQLException {
        return userService.findUserPhotoById(userId);
    }

//    @PutMapping
//    public User updateUser(@RequestBody User user, @RequestHeader("Authorization") String token) throws UserException {
//        User reqUser = userService.findUserByJwt(token);
//        return userService.updateUser(user, reqUser.getId());
//    }

    @PutMapping
    public UserUpdateResponse updateUser(
            @RequestHeader("Authorization") String token,
            @RequestPart("user") User user,
            @RequestPart("photo") MultipartFile photo

    ) throws UserException, SQLException, IOException {
        User reqUser = userService.findUserByJwt(token);
        User created =  userService.updateUser(user, photo, reqUser.getId());
        return UserUpdateResponse.builder()
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
