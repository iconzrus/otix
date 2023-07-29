package com.yuiybishel.Otix.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ChatGPTService {

    private RestTemplate restTemplate;
    private String apiKey = "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";

    public ChatGPTService() {
        this.restTemplate = new RestTemplate();
    }

    public String getChatGPTResponse(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/engines/davinci-codex/completions",
                HttpMethod.POST,
                entity,
                String.class);

        return response.getBody();
    }
}
