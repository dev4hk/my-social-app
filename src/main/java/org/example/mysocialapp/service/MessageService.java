package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Chat;
import org.example.mysocialapp.entity.Message;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.repository.ChatRepository;
import org.example.mysocialapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final ChatService chatService;

    private final ChatRepository chatRepository;

    public Message createMessage(User user, Integer chatId, Message req) {
        Chat chat = chatService.findChatById(chatId);
        Message message = Message.builder()
                .chat(chat)
                .content(req.getContent())
                .image(req.getImage())
                .user(user)
                .timestamp(LocalDateTime.now())
                .build();
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
