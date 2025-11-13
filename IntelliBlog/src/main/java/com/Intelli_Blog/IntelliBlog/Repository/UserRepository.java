package com.Intelli_Blog.IntelliBlog.Repository;

import com.Intelli_Blog.IntelliBlog.Model.Post;
import com.Intelli_Blog.IntelliBlog.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}

