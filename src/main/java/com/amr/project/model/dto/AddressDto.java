package com.amr.project.model.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String cityIndex;
    private String street;
    private String house;
    private String city;
    private String country;

    public AddressDto() {
    }

    public AddressDto(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public AddressDto(String cityIndex, String street, String house, String city, String country) {
        this.cityIndex = cityIndex;
        this.street = street;
        this.house = house;
        this.city = city;
        this.country = country;
    }
}
