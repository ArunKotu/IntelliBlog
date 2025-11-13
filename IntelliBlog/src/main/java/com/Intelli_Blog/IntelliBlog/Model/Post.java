package com.Intelli_Blog.IntelliBlog.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("blog")
public class Post {
    @Id
    private String id;

    private String title;
    private String content;

    private String authorEmail;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

