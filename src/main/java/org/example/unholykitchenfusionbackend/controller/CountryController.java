package org.example.unholykitchenfusionbackend.controller;

import org.example.unholykitchenfusionbackend.dto.CountryResponseDTO;
import org.example.unholykitchenfusionbackend.model.Country;
import org.example.unholykitchenfusionbackend.service.CountriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {
    private final CountriesService countriesService;

    public CountryController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }
    @GetMapping("/search")
    public Mono<String> searchForCountries(@RequestParam String name){
        return countriesService.searchForCountries(name);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CountryResponseDTO.CountryDTO>> findAllCountries() {
        return ResponseEntity.ok(countriesService.fetchAllCountries());
    }
    }

