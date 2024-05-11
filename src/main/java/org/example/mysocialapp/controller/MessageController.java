package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Message;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.service.MessageService;
import org.example.mysocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/messages")
@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageService messageService;

    private final UserService userService;

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<Message> createMessage(@RequestBody Message message, @RequestHeader("Authorization") String token, @PathVariable Integer chatId) {
        User user = userService.findUserByJwt(token);
        return new ResponseEntity<>(messageService.createMessage(user, chatId, message), HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Integer chatId, @RequestHeader("Authorization") String token) {
        userService.findUserByJwt(token);
        return new ResponseEntity<>(messageService.findChatMessages(chatId), HttpStatus.OK);
    }

}
