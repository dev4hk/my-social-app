package org.example.mysocialapp.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mysocialapp.entity.Comment;
import org.example.mysocialapp.entity.Post;
import org.example.mysocialapp.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.example.mysocialapp.util.FileUtils.loadFileString;

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
        this.file = loadFileString(post.getFilePath());
        this.contentType = post.getContentType();
        this.user = post.getUser();
        this.comments = post.getComments();
        this.likedBy = post.getLikedBy();
    }

}
