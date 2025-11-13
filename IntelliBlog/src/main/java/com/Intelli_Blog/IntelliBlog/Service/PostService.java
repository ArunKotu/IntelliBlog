package com.Intelli_Blog.IntelliBlog.Service;

import com.Intelli_Blog.IntelliBlog.Model.Post;
import com.Intelli_Blog.IntelliBlog.Repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import  java.util.*;
@Service
public class PostService {
    private final PostRepository postRepository;
    private final GeminiService service;
    public PostService(PostRepository postRepository, GeminiService service){
        this.postRepository = postRepository;
        this.service = service;
    }
    @Transactional
    public boolean savePost(Post post) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        post.setAuthorEmail(email);
        post.setCreatedAt(LocalDateTime.now());
        boolean isSaved = true;
        try {
        postRepository.save(post);
        return isSaved;
        } catch (Exception e) {
            isSaved = false;
            return isSaved;
        }
    }
    public String summarize(String id){
        Post post = postRepository.findById(id).orElseThrow();
        String content = post.getContent();

        String prompt =
                "Summarize the following blog in 3–4 concise sentences. "
                        + "Do not exceed 4 sentences. "
                        + "Do not include headings, bullet points, or formatting. "
                        + "Return only the summary text.\n\n"
                        + content;

        return service.generateContent(prompt);
    }

    public Post getPostById(String id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthorEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }
        return post;
    }
    public Post updatePost(String id,Post post){
        Optional<Post> oldPost = postRepository.findById(id);
        if(oldPost.isPresent()){
            Post p = oldPost.get();
            p.setTitle(!post.getTitle().isEmpty() ? post.getTitle() : p.getTitle());
            p.setContent(!post.getContent().isEmpty()?post.getContent():p.getContent());
            p.setAuthorEmail(!post.getAuthorEmail().isEmpty()?post.getAuthorEmail():p.getAuthorEmail());
            p.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(p);
        }
        else {
            throw  new RuntimeException("Post Not Found!");
        }

    }
    public String deletePost(String id){

        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            postRepository.deleteById(id);
            return "Deleted Post"+post.get().getId();
        }
        else {
            throw new RuntimeException("Post Not Found! Unable to Delete it");
        }

    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    public List<Post> getAuthorPosts() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return postRepository.findByAuthorEmail(email);
    }

}

