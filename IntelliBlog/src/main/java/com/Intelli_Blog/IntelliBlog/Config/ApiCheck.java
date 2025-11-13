package com.Intelli_Blog.IntelliBlog.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiCheck implements CommandLineRunner {
    @Value("${API_KEY}")
    private String apiKey;
    @Override
    public void run(String... args) throws Exception {
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("WARNING: OPENROUTER API KEY IS MISSING. Set OPENROUTER_API_KEY env var.");
        } else {
            System.out.println("OpenRouter API key loaded (length=" + apiKey.length() + ").");
        }
    }
}
