package org.example.unholykitchenfusionbackend.service;

import org.example.unholykitchenfusionbackend.dto.CountryResponseDTO;
import org.example.unholykitchenfusionbackend.model.Country;
import org.example.unholykitchenfusionbackend.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtractCountries {

    private static final int LIMIT = 100;

    private final RestClient restClient;
    private final CountryRepository repository;

    public ExtractCountries(CountryRepository repository, @Value("${countries.api.key}") String token){
        this.restClient = RestClient.builder()
                .baseUrl("https://api.restcountries.com/countries/v5")
                .defaultHeader("Authorization", token)
                .build();
        this.repository = repository;
    }

    public void extractCountries(){
        if(repository.count() > 0) {
            return;
        }

        List<Country> countries = new ArrayList<>();
        int offset = 0;
        boolean more = true;

        while(more){
            CountryResponseDTO response = restClient.get()
                    .uri("?offset={offset}&limit={limit}&response_fields=names.common,flag.url_png,subregion", offset,LIMIT)
                    .retrieve()
                    .body(CountryResponseDTO.class);

            if(response == null || response.data() == null || response.data().objects() == null){
                break;
            }

            for(CountryResponseDTO.CountryDTO countryDTO : response.data().objects()){
                String name = null;
                if(countryDTO.names() != null){
                    name = countryDTO.names().common();
                }

                String flagUrl = null;
                if(countryDTO.flag().urlPng() != null){
                    flagUrl = countryDTO.flag().urlPng();
                }

                countries.add(new Country(name, flagUrl, countryDTO.subregion()));
            }
            more = response.data().parameters() != null && response.data().parameters().more();
            offset += LIMIT;
        }

        repository.saveAll(countries);

    }

}
