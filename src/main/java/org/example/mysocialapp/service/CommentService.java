package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Comment;
import org.example.mysocialapp.entity.Post;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.repository.CommentRepository;
import org.example.mysocialapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final PostService postService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment =  commentRepository.save(comment);
        post.getComments().add(savedComment);
        postRepository.save(post);
        return savedComment;
    }

    public Comment findCommentById(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

    }

    public Comment likeComment(Integer commentId, Integer userId) throws UserException {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);
        if(!comment.getLiked().contains(user)) {
            comment.getLiked().add(user);
        }
        else {
            comment.getLiked().remove(user);
        }
        return commentRepository.save(comment);
    }
}
