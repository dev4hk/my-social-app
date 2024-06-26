package org.example.mysocialapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Message;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.exception.UserException;
import org.example.mysocialapp.response.MessageResponse;
import org.example.mysocialapp.service.MessageService;
import org.example.mysocialapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/messages")
@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageService messageService;

    private final UserService userService;

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<Message> createMessage(
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String token,
            @PathVariable Integer chatId)
            throws UserException, IOException {
        User user = userService.findUserByJwt(token);
        return new ResponseEntity<>(messageService.createMessage(user, chatId, content, file), HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Integer chatId, @RequestHeader("Authorization") String token) throws UserException {
        userService.findUserByJwt(token);
        return new ResponseEntity<>(messageService.findChatMessages(chatId), HttpStatus.OK);
    }

}
