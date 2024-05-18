package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Post;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.response.ApiResponse;
import org.example.mysocialapp.response.PostResponse;
import org.example.mysocialapp.service.PostService;
import org.example.mysocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam("caption") String caption,
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String token
    ) throws UserException, IOException {
        User reqUser = userService.findUserByJwt(token);
        return new ResponseEntity<>(postService.createNewPost(caption, file, reqUser.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException {
        User reqUser = userService.findUserByJwt(token);
        ApiResponse res = new ApiResponse(postService.deletePost(postId, reqUser.getId()), true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException {
        userService.findUserByJwt(token);
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> findPostsByUser(@PathVariable Integer userId, @RequestHeader("Authorization") String token) throws UserException, IOException {
        userService.findUserByJwt(token);
        return new ResponseEntity<>(postService.findPostsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAllPosts(@RequestHeader("Authorization") String token) throws IOException, UserException {
        userService.findUserByJwt(token);
        return new ResponseEntity<>(postService.findAllPost(), HttpStatus.OK);
    }

    @PutMapping("/save/{postId}")
    public ResponseEntity<Post> savePostById(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException {
        User reqUser = userService.findUserByJwt(token);
        return new ResponseEntity<>(postService.saveUnsavePost(postId, reqUser.getId()), HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<PostResponse> likePostById(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, IOException {
        User reqUser = userService.findUserByJwt(token);
        return new ResponseEntity<>(postService.likeUnlikePost(postId, reqUser.getId()), HttpStatus.OK);
    }


}
