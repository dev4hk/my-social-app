package org.example.mysocialapp.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.mysocialapp.entity.Comment;
import org.example.mysocialapp.entity.Post;
import org.example.mysocialapp.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class PostResponse {
    private Integer id;
    private String caption;
    private String fileName;
    private String file;
    private String contentType;
    private User user;
    private List<Comment> comments;
    private Set<User> likedBy;


    public PostResponse(Post post) throws IOException {
        this.id = post.getId();
        this.caption = post.getCaption();
        this.fileName = post.getFileName();
        this.file = loadFileString(post);
        this.contentType = post.getContentType();
        this.user = post.getUser();
        this.comments = post.getComments();
        this.likedBy = post.getLikedBy();
    }

    private String loadFileString(Post post) throws IOException {
        Resource resource = null;
        if(!post.getFilePath().isEmpty()) {
            resource = getResource(post.getFilePath());
        }
        byte[] fileBytes = Objects.requireNonNull(resource).getContentAsByteArray();
        return Base64.encodeBase64String(fileBytes);
    }

    private Resource getResource(String filePath) throws FileNotFoundException, MalformedURLException {
        if(!Files.exists(Path.of(filePath))) {
            throw new FileNotFoundException(filePath + " was not found.");
        }
        return new UrlResource(Path.of(filePath).toUri());
    }
}
