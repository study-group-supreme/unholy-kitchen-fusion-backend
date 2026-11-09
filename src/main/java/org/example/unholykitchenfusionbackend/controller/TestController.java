package org.example.unholykitchenfusionbackend.controller;

import org.aspectj.weaver.ast.Test;
import org.example.unholykitchenfusionbackend.service.CountriesService;
import org.example.unholykitchenfusionbackend.service.LlmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    private final CountriesService countriesService;
    private final LlmService llmService;

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
                "You write a short, fun mashup of two countries. Max 80 words.",   // system: how to behave
                "Country 1: " + a + "\nCountry 2: " + b);                          // user: the actual request
    }
}
