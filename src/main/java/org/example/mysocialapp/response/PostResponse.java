package org.example.mysocialapp.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.mysocialapp.entity.User;

@Data
@NoArgsConstructor
public class PostResponse {
    private Integer id;
    private String caption;
    private String fileName;
    private String file;
    private String contentType;
    private User user;

    public PostResponse(Integer id, String caption, String fileName, byte[] fileBytes, String contentType, User user) {
        this.id = id;
        this.caption = caption;
        this.fileName = fileName;
        this.file = fileBytes == null ? null : Base64.encodeBase64String(fileBytes);
        this.contentType = contentType;
        this.user = user;
    }
}
