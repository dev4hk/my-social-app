package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Post;
import org.example.mysocialapp.response.ApiResponse;
import org.example.mysocialapp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Integer userId) {
        return new ResponseEntity<>(postService.createNewPost(post, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}/user/{userId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @PathVariable Integer userId) {
        ApiResponse res = new ApiResponse(postService.deletePost(postId, userId), true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId) {
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> findPostsByUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(postService.findPostsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Post>> findAllPosts() {
        return new ResponseEntity<>(postService.findAllPost(), HttpStatus.OK);
    }

    @PutMapping("/save/{postId}/user/{userId}")
    public ResponseEntity<Post> savePostById(@PathVariable Integer postId, @PathVariable Integer userId) {
        return new ResponseEntity<>(postService.saveUnsavePost(postId, userId), HttpStatus.OK);
    }

    @PutMapping("/like/{postId}/user/{userId}")
    public ResponseEntity<Post> likePostById(@PathVariable Integer postId, @PathVariable Integer userId) {
        return new ResponseEntity<>(postService.likeUnlikePost(postId, userId), HttpStatus.OK);
    }


}
