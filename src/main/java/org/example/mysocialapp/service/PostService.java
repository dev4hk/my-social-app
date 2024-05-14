package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Post;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.repository.PostRepository;
import org.example.mysocialapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public static final String Directory = System.getProperty("user.home") + "/Downloads/uploads/";

//    public Post createNewPost(Post post, Integer userId) throws UserException {
//        Post newPost = Post.builder()
//                .caption(post.getCaption())
//                .image(post.getImage())
//                .createdAt(LocalDateTime.now())
//                .video(post.getVideo())
//                .user(userService.findUserById(userId))
//                .build();
//        return postRepository.save(newPost);
//    }

//    public Post createNewPost(Post post, MultipartFile file, Integer userId) throws UserException, IOException {
//
//        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//        String filePath = Directory + userId;
//        Files.createDirectories(Paths.get(filePath));
//        copy(file.getInputStream(), Paths.get(Directory + userId + "/" + filename), REPLACE_EXISTING);
//
//        Post newPost = Post.builder()
//                .caption(post.getCaption())
//                .fileName(filename)
//                .createdAt(LocalDateTime.now())
//                .user(userService.findUserById(userId))
//                .contentType(file.getContentType())
//                .filePath(filePath)
//                .build();
//
//        return postRepository.save(newPost);
//    }

    public Post createNewPost(String caption, MultipartFile file, Integer userId) throws UserException, IOException {

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String filePath = Directory + userId;
        Files.createDirectories(Paths.get(filePath));
        copy(file.getInputStream(), Paths.get(Directory + userId + "/" + filename), REPLACE_EXISTING);

        Post newPost = Post.builder()
                .caption(caption)
                .fileName(filename)
                .createdAt(LocalDateTime.now())
                .user(userService.findUserById(userId))
                .contentType(file.getContentType())
                .filePath(filePath)
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
        if (user.getSavedPosts().contains(post)) {
            user.getSavedPosts().remove(post);
        } else {
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
