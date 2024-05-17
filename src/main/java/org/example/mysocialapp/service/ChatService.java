package org.example.mysocialapp.service;

import lombok.RequiredArgsConstructor;
import org.example.mysocialapp.entity.Chat;
import org.example.mysocialapp.entity.User;
import org.example.mysocialapp.repository.ChatRepository;
import org.example.mysocialapp.response.ChatResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat createChat(User reqUser, User user2) {
        Optional<Chat> existingChat = chatRepository.findChatByUsersId(user2, reqUser);
        if (existingChat.isPresent()) {
            return existingChat.get();
        }

        Chat chat = new Chat();
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setTimestamp(LocalDateTime.now());

        return chatRepository.save(chat);

    }

    public Chat findChatById(Integer chatId){
        return chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
    }

    public List<Chat> findUsersChat(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }

//
//    public List<ChatResponse> findUsersChat(Integer userId) throws IOException {
//        List<Chat> chats =  chatRepository.findByUsersId(userId);
//        List<ChatResponse> responses = new ArrayList<>();
//        for(Chat chat : chats) {
//            ChatResponse chatResponse = new ChatResponse(chat);
//            responses.add(chatResponse);
//        }
//        return responses;
//    }
}
