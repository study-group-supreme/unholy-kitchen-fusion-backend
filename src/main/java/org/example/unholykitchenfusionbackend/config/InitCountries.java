package org.example.unholykitchenfusionbackend.config;

import org.example.unholykitchenfusionbackend.repository.CountryRepository;
import org.example.unholykitchenfusionbackend.service.ExtractCountries;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitCountries implements CommandLineRunner {

    private final ExtractCountries extractor;

    public InitCountries(ExtractCountries extractor){
        this.extractor = extractor;
    }

    @Override
    public void run(String... args) throws Exception {
        extractor.extractCountries();
    }
}
