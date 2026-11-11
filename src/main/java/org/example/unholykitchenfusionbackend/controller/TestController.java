package org.example.unholykitchenfusionbackend.controller;

import org.aspectj.weaver.ast.Test;
import org.example.unholykitchenfusionbackend.service.CountriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    private final CountriesService countriesService;

    public TestController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/test/{name}")
    public Mono<String> test(@PathVariable String name){
        return countriesService.fetchCountryByName(name);
    }
}
