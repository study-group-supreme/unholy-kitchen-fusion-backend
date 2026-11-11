package org.example.unholykitchenfusionbackend.controller;

import org.example.unholykitchenfusionbackend.service.CountriesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api")
public class CountryController {
    private final CountriesService countriesService;

    public CountryController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> searchForCountries(@RequestParam String name){
        return countriesService.searchForCountries(name);
    }
}
