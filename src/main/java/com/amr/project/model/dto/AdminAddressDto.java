package com.amr.project.model.dto;

import lombok.Data;

@Data
public class AdminAddressDto {
    private Long id;
    private String cityIndex;
    private String street;
    private String house;
    private AdminCityDto city;
    private CountryDto country;
}
