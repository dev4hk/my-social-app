package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    public User followUser(Integer userId1, Integer userId2) {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);
        user2.getFollowers().add(user1.getId());
        user1.getFollowings().add(user2.getId());
        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    public User updateUser(User user, Integer userId) {
        User oldUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setPassword(user.getPassword());
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

}
