package org.example.mysocialapp.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mysocialapp.entity.Chat;
import org.example.mysocialapp.entity.Message;
import org.example.mysocialapp.entity.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChatResponse {

    private Integer id;

    private String chat_name;

    private String chat_image;

    private List<User> users;

    private LocalDateTime timestamp;

    private List<MessageResponse> messages;

    public ChatResponse(Chat chat) throws IOException {
        this.id = chat.getId();
        this.chat_name = chat.getChat_name();
        this.chat_image = chat.getChat_image();
        this.users = chat.getUsers();
        this.timestamp = chat.getTimestamp();
        List<MessageResponse> messageResponses = new ArrayList<>();
        for(Message message : chat.getMessages()) {
            messageResponses.add(new MessageResponse(message));
        }
        this.messages = messageResponses;
    }
}
