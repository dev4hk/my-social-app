package org.example.mysocialapp.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mysocialapp.entity.Chat;
import org.example.mysocialapp.entity.Message;
import org.example.mysocialapp.entity.User;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.example.mysocialapp.util.FileUtils.loadFileString;

@Data
@NoArgsConstructor
public class MessageResponse {

    private Integer id;
    private String content;
    private String fileName;
    private String file;
    private String contentType;
    private User user;
    private LocalDateTime timestamp;

    public MessageResponse(Message message) throws IOException {
        this.id = message.getId();
        this.content = message.getContent();
        this.fileName = message.getFileName();
        this.file = loadFileString(message.getFilePath());
        this.contentType = message.getContentType();
        this.user = message.getUser();
        this.timestamp = message.getTimestamp();
    }

}
