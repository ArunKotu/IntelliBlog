package com.Intelli_Blog.IntelliBlog.Repository;

import com.Intelli_Blog.IntelliBlog.Model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post,String> {
    Post findByTitle(String title);
    List<Post> findByAuthorEmail(String authorName);
    Post findByTitleAndAuthorEmail(String title, String authorUsername);

}
