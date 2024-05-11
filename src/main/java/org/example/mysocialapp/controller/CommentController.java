package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Comment;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.service.CommentService;
import org.example.mysocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> createComment(
            @PathVariable Integer postId,
            @RequestBody Comment comment,
            @RequestHeader("Authorization") String token
    ) {
        User user = userService.findUserByJwt(token);
        return new ResponseEntity<>(commentService.createComment(comment, postId, user.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<Comment> likeComment(
            @PathVariable Integer commentId,
            @RequestHeader("Authorization") String token
    ) {
        User user = userService.findUserByJwt(token);
        return new ResponseEntity<>(commentService.likeComment(commentId, user.getId()), HttpStatus.CREATED);
    }

}
