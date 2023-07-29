package com.yuiybishel.Otix.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ChatGPTService {

    private RestTemplate restTemplate;
    private String apiKey = "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";

    public ChatGPTService() {
        this.restTemplate = new RestTemplate();
    }

    public String getChatGPTResponse(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем тело запроса с сообщением пользователя
        Map<String, Object> body = new HashMap<>();
        body.put("prompt", message);
        body.put("max_tokens", 60);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/engines/davinci-codex/completions",
                HttpMethod.POST,
                entity,
                String.class);

        return response.getBody();
    }
}
