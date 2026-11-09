package org.example.unholykitchenfusionbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClaudeRequest(String model,
                            @JsonProperty("max_tokens") int maxTokens,
                            String system,                                  // top-level, NOT a message
                            List<Message> messages,
                            @JsonProperty("output_config") OutputConfig outputConfig) {
    public record Message(String role, String content) {}
    public record OutputConfig(String effort) {}
}
