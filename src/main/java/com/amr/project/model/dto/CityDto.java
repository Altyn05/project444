package com.amr.project.model.dto;

import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityDto {
    private Long id;
    private String name;
    private CountryDto location;
    private List<AddressDto> addresses;

    public CityDto(String name) {
        this.name = name;
    }

    public CityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
