package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Chat;
import org.example.mysocialapp.entity.Message;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.repository.ChatRepository;
import org.example.mysocialapp.repository.MessageRepository;
import org.example.mysocialapp.response.MessageResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RequiredArgsConstructor
@Service
public class MessageService {

    public static final String Directory = System.getProperty("user.home") + "/Downloads/uploads/";

    private final MessageRepository messageRepository;

    private final ChatService chatService;

    private final ChatRepository chatRepository;

    public Message createMessage(User user, Integer chatId, String content, MultipartFile file) throws IOException {
        if(content == null && file == null) {
            throw new RuntimeException("Cannot create message because content and file are empty");
        }
        if(Objects.requireNonNull(content).isBlank() && Objects.requireNonNull(file).getSize() == 0) {
            throw new RuntimeException("Cannot create message because content and file are empty");
        }
        Chat chat = chatService.findChatById(chatId);
        String filePath = null;
        String fileName = null;
        String contentType = null;
        if(file != null && file.getSize() != 0) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            filePath = Directory + user.getId() + "/chat/" + chatId;
            contentType = file.getContentType();
            Files.createDirectories(Paths.get(filePath));
            copy(file.getInputStream(), Paths.get(filePath + "/" + fileName), REPLACE_EXISTING);
        }
        Message message = Message.builder()
                .chat(chat)
                .content(content)
                .user(user)
                .contentType(contentType)
                .timestamp(LocalDateTime.now())
                .build();

        if(filePath != null) {
            message.setFilePath(filePath + "/" + fileName);
            message.setFileName(fileName);
        }

        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
        return savedMessage;
    }

    public List<Message> findChatMessages(Integer chatId) {
        chatService.findChatById(chatId);
        return messageRepository.findByChatId(chatId);
    }

}
