package com.amr.project.model.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegionDto {
    private Long id;
    private String name;
    private CountryDto country;
    private List<CityDto> cities;

    public RegionDto(String name){
        this.name = name;
    }
}
