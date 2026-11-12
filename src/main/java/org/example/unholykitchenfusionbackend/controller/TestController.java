package org.example.unholykitchenfusionbackend.controller;

import org.aspectj.weaver.ast.Test;
import org.example.unholykitchenfusionbackend.model.Country;
import org.example.unholykitchenfusionbackend.service.CountriesService;
import org.example.unholykitchenfusionbackend.service.LlmService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class TestController {
    private final CountriesService countriesService;
    private final LlmService llmService;
    private final String recipeSystemMessage = "You will take this list of countries and create a fusion recipe using " +
            "their distinct food cultures. It will need to include both an ingredient list and step by step guide." +
            "Omit equipment needed list, and keep the recipe simple";

    public TestController(CountriesService countriesService, LlmService llmService) {
        this.countriesService = countriesService;
        this.llmService = llmService;
    }

    @GetMapping("/test/{name}")
    public Mono<String> test(@PathVariable String name){
        return countriesService.fetchCountryByName(name);
    }


    // Try: http://localhost:8080/test/mashup?a=Sweden&b=Japan
    @GetMapping("/test/mashup")
    public String mashup(@RequestParam String a, @RequestParam String b) {
        return llmService.ask(
                "You write a short, fun mashup of two countries. Max 80 words.",   // systemMessage: how to behave, might be moved to Service layer
                "Country 1: " + a + "\nCountry 2: " + b);                          // user: the actual request
    }

//    @GetMapping("/test/list-pass")
//    public String sendList(@RequestBody List<Country> countries) {

    @PostMapping("/test/create-recipe")
    public String createRecipe(@RequestBody List<Country> countries) {
        return llmService.ask(recipeSystemMessage, llmService.constructPrompt(countries));
    }
}
