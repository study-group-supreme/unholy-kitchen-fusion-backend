package org.example.unholykitchenfusionbackend.controller;

import org.example.unholykitchenfusionbackend.dto.RecipeResponse;
import org.example.unholykitchenfusionbackend.model.Country;
import org.example.unholykitchenfusionbackend.service.CountriesService;
import org.example.unholykitchenfusionbackend.service.LlmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {
    private final LlmService llmService;
    private final String recipeSystemMessage = "You will take this list of countries and create a fusion recipe using " +
            "their distinct food cultures. It will need to include both an ingredient list and step by step guide." +
            "Omit equipment needed list, and keep the recipe simple";


    public RecipeController(LlmService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/create-recipe")
    public RecipeResponse createRecipe(@RequestBody List<Country> countries) {
        String recipeResult = llmService.ask(recipeSystemMessage, llmService.constructPrompt(countries));
        return new RecipeResponse(recipeResult);
    }
}
