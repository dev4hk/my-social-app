package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.repository.UserRepository;
import org.example.mysocialapp.security.JwtProvider;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user) {
        User newUser = User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .followers(new HashSet<>())
                .followings(new HashSet<>())
                .savedPosts(new HashSet<>())
                .id(user.getId())
                .build();

        return userRepository.save(newUser);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User followUser(Integer reqUserId, Integer userId2) {
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);
        user2.getFollowers().add(reqUser.getId());
        reqUser.getFollowings().add(user2.getId());
        userRepository.save(reqUser);
        userRepository.save(user2);
        return reqUser;
    }

    public User updateUser(User user, Integer userId) {
        User oldUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if(user.getFirstName() != null) {
            oldUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null) {
            oldUser.setLastName(user.getLastName());
        }
        if(user.getGender() != null) {
            oldUser.setGender(user.getGender());
        }

        return userRepository.save(oldUser);
    }

    public List<User> searchUsers(String query) {
        return userRepository.searchUser(query);
    }

    public String deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return "User deleted";
    }

    public User findUserByJwt(String token) {
        String email = JwtProvider.getEmailFromJwtToken(token);
        User user =  userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(null);
        return user;
    }

}
