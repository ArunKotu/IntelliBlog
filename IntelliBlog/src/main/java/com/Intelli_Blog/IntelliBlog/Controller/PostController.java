package com.Intelli_Blog.IntelliBlog.Controller;

import com.Intelli_Blog.IntelliBlog.Model.Post;
import com.Intelli_Blog.IntelliBlog.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }


    @PostMapping("/save")
    public ResponseEntity<String> savePost(@RequestBody Post post){
        if(postService.savePost(post)){
            return ResponseEntity.ok("Saved!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/summarize/{id}")
    public String summarize(@PathVariable String id){
        return postService.summarize(id);
    }
    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable String id){
        return postService.deletePost(id);
    }
    @PutMapping("/update/{id}")
    public Post updatePost(@PathVariable String id,@RequestBody Post post){
        return postService.updatePost(id,post);
    }
    @GetMapping("/getPost/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @GetMapping("/getPosts")
    public ResponseEntity<List<Post>> getPosts(){
        return ResponseEntity.ok(postService.getAuthorPosts());
    }
}
