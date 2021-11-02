package com.amr.project.converter;

import com.amr.project.model.dto.CountryDto;
import com.amr.project.model.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    List<CountryDto> countryListToListCountryDto(List<Country> countries);
    CountryDto countryToCountryDto(Country country);
}
