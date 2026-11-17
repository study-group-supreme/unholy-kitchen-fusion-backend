package org.example.unholykitchenfusionbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String flagURL;
    private String subRegion;

    public Country (){}

    public Country(String name, String flagURL, String subRegion) {
        this.name = name;
        this.flagURL = flagURL;
        this.subRegion = subRegion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    @Override
    public String toString() {
        return "Country{" +
                "names='" + name + '\'' +
                ", flagURL='" + flagURL + '\'' +
                ", subRegion='" + subRegion + '\'' +
                '}';
    }
}
