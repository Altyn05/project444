package com.amr.project.model.dto;

import lombok.Data;

@Data
public class AdminRegionDto {
    private Long id;
    private String name;
    private CountryDto country;
}
