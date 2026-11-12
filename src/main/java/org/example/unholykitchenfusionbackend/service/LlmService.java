package org.example.unholykitchenfusionbackend.service;

import org.example.unholykitchenfusionbackend.dto.ClaudeRequest;
import org.example.unholykitchenfusionbackend.dto.ClaudeResponse;
import org.example.unholykitchenfusionbackend.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class LlmService {

    private static final Logger log = LoggerFactory.getLogger(LlmService.class);
    private final WebClient client;
    private final String model, effort;
    private final int maxTokens;

    private ObjectMapper objectMapper;

    public LlmService(WebClient.Builder builder,
                      @Value("${llm.base-url}") String baseUrl,
                      @Value("${llm.api-key}") String apiKey,
                      @Value("${llm.anthropic-version}") String version,
                      @Value("${llm.model}") String model,
                      @Value("${llm.max-tokens}") int maxTokens,
                      @Value("${llm.effort}") String effort,
                      ObjectMapper objectMapper) {
        this.client = builder.baseUrl(baseUrl)
                .defaultHeader("x-api-key", apiKey)           // sent on every request
                .defaultHeader("anthropic-version", version)
                .build();
        this.model = model;
        this.maxTokens = maxTokens;
        this.effort = effort;
        this.objectMapper = objectMapper;
    }

    public String ask(String systemMessage, String userPrompt) {
        var body = new ClaudeRequest(model, maxTokens, systemMessage,
                List.of(new ClaudeRequest.Message("user", userPrompt)),
                new ClaudeRequest.OutputConfig(effort));
        try {
            ClaudeResponse response = client.post().uri("/v1/messages")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)                           // record -> JSON by Jackson
                    .retrieve().bodyToMono(ClaudeResponse.class).block();
            System.out.println(response.usage().output_tokens());
            // the first block is not always text, so pick the first "text" block
            return response.content().stream().filter(b -> "text".equals(b.type()))
                    .map(ClaudeResponse.ContentBlock::text).findFirst()
                    .orElseThrow(() -> new IllegalStateException("No text, stop_reason=" + response.stopReason()));
        } catch (WebClientResponseException e) {
            log.error("LLM error {}: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "The AI service failed, try again");
        }
    }

    public String constructPrompt(List<Country> countries) {
        return objectMapper.writeValueAsString(countries);
    }
}