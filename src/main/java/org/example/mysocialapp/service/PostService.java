package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Post;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.repository.PostRepository;
import org.example.mysocialapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public Post createNewPost(Post post, Integer userId) throws UserException {
        Post newPost = Post.builder()
                .caption(post.getCaption())
                .image(post.getImage())
                .createdAt(LocalDateTime.now())
                .video(post.getVideo())
                .user(userService.findUserById(userId))
                .build();
        return postRepository.save(newPost);
    }

    public String deletePost(Integer postId, Integer userId) throws UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this post");
        }
        postRepository.delete(post);
        return "Post deleted";
    }

    public List<Post> findPostsByUserId(Integer userId) {
        return postRepository.findAllPostsByUserId(userId);
    }

    public Post findPostById(Integer postId) {
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    public Post saveUnsavePost(Integer postId, Integer userId) throws UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(user.getSavedPosts().contains(post)) {
            user.getSavedPosts().remove(post);
        }
        else {
            user.getSavedPosts().add(post);
        }
        userRepository.save(user);
        return post;
    }

    public Post likeUnlikePost(Integer postId, Integer userId) throws UserException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (post.getLikedBy().contains(user)) {
            post.getLikedBy().remove(user);
        } else {
            post.getLikedBy().add(user);
        }

        return postRepository.save(post);
    }
}
