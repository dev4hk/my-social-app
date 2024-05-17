package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Chat;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.request.CreateChatRequest;
import org.example.mysocialapp.response.ChatResponse;
import org.example.mysocialapp.service.ChatService;
import org.example.mysocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/chats")
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody CreateChatRequest request, @RequestHeader("Authorization") String token) throws UserException {
        User reqUser = userService.findUserByJwt(token);
        User user2 = userService.findUserById(request.getUserId());
        return new ResponseEntity<>(chatService.createChat(reqUser, user2), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Chat>> findUsersChat(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserByJwt(token);
        return new ResponseEntity<>(chatService.findUsersChat(user.getId()), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<ChatResponse>> findUsersChat(@RequestHeader("Authorization") String token) throws UserException, IOException {
//        User user = userService.findUserByJwt(token);
//        return new ResponseEntity<>(chatService.findUsersChat(user.getId()), HttpStatus.OK);
//    }
}
