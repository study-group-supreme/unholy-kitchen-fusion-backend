package org.example.unholykitchenfusionbackend.service;

import org.example.unholykitchenfusionbackend.dto.CountryResponseDTO;
import org.example.unholykitchenfusionbackend.model.Country;
import org.example.unholykitchenfusionbackend.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CountriesService {
    private final WebClient webClient;
    private final CountryRepository countryRepository;

    @Value("${countries.api.key}")
    private String apikey;

    public CountriesService(WebClient.Builder webClientBuilder, CountryRepository countryRepository) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.restcountries.com/countries/v5")
                .build();
        this.countryRepository = countryRepository;
    }

    /**
     * bodyToMono(CountryResponseDTO.class) makes Jackson parse the JSON into our record class. A field not in the record is ignored.
     * .map(...) removes the data/meta wrapper, so the frontend gets a plain array like /api/all.
     * @param name
     * @return
     */
    public Mono<List<CountryResponseDTO.CountryDTO>> searchForCountries(String name) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("q", name)
                    .queryParam("response_fields", "names.common,flag.url_png,subregion")
                        .build())
                .header("Authorization", apikey)
                .retrieve()
                .bodyToMono(CountryResponseDTO.class) // JSON -> Java object, instead of String
                .map(response -> response.data() == null || response.data().objects() == null
                        ? List.<CountryResponseDTO.CountryDTO>of()
                        : response.data().objects());
    }

    public List<CountryResponseDTO.CountryDTO> fetchAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private CountryResponseDTO.CountryDTO toDTO(Country country) {
        return new CountryResponseDTO.CountryDTO(
                new CountryResponseDTO.Names(country.getName()),
                new CountryResponseDTO.Flag(country.getFlagURL()),
                country.getSubRegion()
        );
    }
}