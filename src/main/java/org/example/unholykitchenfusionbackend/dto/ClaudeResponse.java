package org.example.unholykitchenfusionbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)             // the response has many fields we don't need
public record ClaudeResponse(List<ContentBlock> content,
                             @JsonProperty("stop_reason") String stopReason) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ContentBlock(String type, String text) {}
}
