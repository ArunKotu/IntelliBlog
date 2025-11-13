package com.Intelli_Blog.IntelliBlog.Controller;

import com.Intelli_Blog.IntelliBlog.Service.GeminiService;
import com.Intelli_Blog.IntelliBlog.Service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/ai/gemini")
public class GeminiController {
    private final PostService postService;
    private final GeminiService service;

    public GeminiController(PostService postService, GeminiService service) {
        this.postService = postService;
        this.service = service;
    }
   @PostMapping("/generate")
public ResponseEntity<String> generate(@RequestBody String blogText) {
    try {
        String prompt =
                "Summarize the following blog in 3–4 concise sentences. "
                        + "Do not exceed 4 sentences. "
                        + "Do not include headings, bullet points, or formatting. "
                        + "Return only the summary text.\n\n"
                        + blogText;

        return ResponseEntity.ok(service.generateContent(prompt));

    } catch (Exception e) {
        return ResponseEntity.status(500).body("AI summarization failed.");
    }
}

    @GetMapping("/summarize/{postId}")
    public String summarizeFromDb(@PathVariable String postId) {
        return postService.summarize(postId);
    }
}
