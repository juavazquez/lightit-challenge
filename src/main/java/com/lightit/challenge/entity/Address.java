package com.lightit.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    // Saved Everything as String to make it easier
    // normally i would create a separate class for city, state and country
    // with connections to each other (city -> state -> country)

    @Column(name = "street_line")
    private String streetLine;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;
}