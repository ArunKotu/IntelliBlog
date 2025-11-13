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
    RestClient client = RestClient.create();
    ObjectMapper mapper = new ObjectMapper();
    private String extractText(String response) {
        try {
            JsonNode root = mapper.readTree(response);
            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText()
                    .trim();
        } catch (Exception e) {
            return "Summary generation failed.";
        }
    }

    public String generateContent(String prompt) {
    try {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + "gemini-2.5-flash-lite:generateContent?key=" + apiKey;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
        );

        String response = client.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(String.class);

        return extractText(response);

    } catch (Exception e) {
        return "AI summary generation failed. (Backend Error)";
    }
}

}
