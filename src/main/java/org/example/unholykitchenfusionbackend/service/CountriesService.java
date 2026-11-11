package org.example.unholykitchenfusionbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CountriesService {
    private final WebClient webClient;

    @Value("${countries.api.key}")
    private String apikey;

    public CountriesService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.restcountries.com/countries/v5")
                .build();
    }

    public Mono<String> fetchCountryByName(String name) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/name")
                        .queryParam("q", name)
                        .queryParam("response_fields", "names.common,codes.alpha_2,flag.emoji")
                        .build())
                .header("Authorization", apikey)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> searchForCountries(String name) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", name)
                        .build())
                .header("Authorization", apikey)
                .retrieve()
                .bodyToMono(String.class);
    }
}