package com.amr.project.model.dto;

import lombok.Data;

@Data
public class AdminCityDto {
    private Long id;
    private String name;
    private CountryDto country;
}
