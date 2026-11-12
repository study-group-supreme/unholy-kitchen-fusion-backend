package org.example.unholykitchenfusionbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//not needed, but safety net added by claude in case some fields would be left empty
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClaudeRequest(String model,
                            @JsonProperty("max_tokens") int maxTokens,
                            // top-level, NOT a message
                            @JsonProperty("system") String systemMessage,
                            List<Message> messages,
                            @JsonProperty("output_config") OutputConfig outputConfig
) {
    public record Message(String role, String content) {
    }

    public record OutputConfig(String effort) {
    }

    public record RecipeResponse(String recipe) {}
}
