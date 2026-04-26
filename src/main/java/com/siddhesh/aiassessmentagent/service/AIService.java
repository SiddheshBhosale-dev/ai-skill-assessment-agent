package com.siddhesh.aiassessmentagent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class AIService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create("https://api.groq.com/openai/v1");

    public Map<String, Object> callAI(String prompt) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.1-8b-instant");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", prompt));

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        return webClient.post()
                .uri("/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> choices =
                            (List<Map<String, Object>>) response.get("choices");

                    Map<String, Object> message =
                            (Map<String, Object>) choices.get(0).get("message");

                    String content = message.get("content").toString();


                    try {
                        return new com.fasterxml.jackson.databind.ObjectMapper()
                                .readValue(content, Map.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Invalid JSON from AI: " + content);
                    }
                })
                .block();
    }
}
