package com.amr.project.model.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String cityIndex;
    private String street;
    private String house;
    private String city;
    private String region;
    private String country;

    public AddressDto() {
    }

    public AddressDto(String city, String region, String country) {
        this.city = city;
        this.region = region;
        this.country = country;
    }

    public AddressDto(String cityIndex, String street, String house, String city, String region, String country) {
        this.cityIndex = cityIndex;
        this.street = street;
        this.house = house;
        this.city = city;
        this.region = region;
        this.country = country;
    }
}
