package org.example.unholykitchenfusionbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)             // the response has many fields we don't need, but spring booth handles it in our case
public record ClaudeResponse(List<ContentBlock> content,
                             @JsonProperty("stop_reason") String stopReason, Usage usage) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ContentBlock(String type, String text) {}
    public record Usage(int input_tokens, int output_tokens) {}
}
