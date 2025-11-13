package com.Intelli_Blog.IntelliBlog.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${API_KEY}")
    private String apiKey;

    private final RestClient client = RestClient.create();
    private final ObjectMapper mapper = new ObjectMapper();

    public String generateContent(String prompt) {
        try {
            String url = "https://openrouter.ai/api/v1/chat/completions";

            Map<String, Object> requestBody = Map.of(
                    "model", "google/gemini-flash-1.5",
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt
                            )
                    )
            );

            String response = client.post()
                    .uri(url)
                    .header("Authorization", "Bearer " + apiKey)
                    .header("HTTP-Referer", "https://your-domain.com")
                    .header("X-Title", "IntelliBlog") 
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            JsonNode root = mapper.readTree(response);

            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText()
                    .trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "AI summary generation failed.";
        }
    }
}
