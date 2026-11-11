package org.example.unholykitchenfusionbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CountryResponseDTO(Data data) {
    public record Data(List<CountryDTO> objects, @JsonProperty("meta") RequestParameters parameters){}

    public record RequestParameters (int total, int count, int limit, int offset, boolean more){}

    public record CountryDTO(Names names, Flag flag, String subregion){}

    public record Names(String common){}

    public record Flag(@JsonProperty("url_png") String urlPng){}
}
