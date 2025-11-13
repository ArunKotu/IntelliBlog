package com.Intelli_Blog.IntelliBlog.Service;

import com.Intelli_Blog.IntelliBlog.Model.GenerateContentRequest;
import com.Intelli_Blog.IntelliBlog.Model.GenerateContentResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Objects;
import java.util.Optional;

@Service
public class GeminiService {
    
    private final String apiUrl;
    private final RestClient client;
    
    public GeminiService(
        RestClient.Builder restClientBuilder,
        @Value("${API_KEY}") String apiKey,
        @Value("${gemini.base-url:https://generativelanguage.googleapis.com/v1beta/models/}") String baseUrl,
        @Value("${gemini.model-name:gemini-2.5-flash-lite}") String modelName,
        @Value("${gemini.endpoint:generateContent?key=}") String endpoint
    ) {
        this.apiUrl = baseUrl + modelName + endpoint + apiKey;
        this.client = restClientBuilder.build();
    }

    private Optional<String> extractText(GenerateContentResponse response) {
        return Optional.ofNullable(response)
                .flatMap(r -> r.candidates().stream().findFirst())
                .map(c -> c.content())
                .flatMap(c -> c.parts().stream().findFirst())
                .map(p -> p.text())
                .filter(Objects::nonNull)
                .map(String::trim);
    }

    public String generateContent(String prompt) {
        GenerateContentRequest requestBody = GenerateContentRequest.fromPrompt(prompt);

        try {
            GenerateContentResponse response = client.post()
                    .uri(apiUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(GenerateContentResponse.class); 

            return extractText(response)
                    .orElseThrow(() -> new RuntimeException("Gemini API response was empty or did not contain readable text."));

        } catch (RestClientException e) {
            System.err.println("API Call Failed: Could not connect or received an HTTP error from Gemini.");
            System.err.println("Error details: " + e.getMessage());
            
            throw new RuntimeException("Gemini API call failed due to external or network error. Check application logs.", e);
        }
    }
}
