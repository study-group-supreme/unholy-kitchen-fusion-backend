package org.example.unholykitchenfusionbackend.repository;

import org.example.unholykitchenfusionbackend.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
