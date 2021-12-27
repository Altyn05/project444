package com.amr.project.converter;

import com.amr.project.model.dto.AdminCityDto;
import com.amr.project.model.dto.CityDto;
import com.amr.project.model.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {
//    @Mapping(target = "region", source = "region.name")
//    CityDto toDto(City city);
//    @Mapping(target = "region.name", source = "region")
//    City toModel(CityDto cityDto);

    List<AdminCityDto> cityListToListAdminCityDto(List<City> cities);
    AdminCityDto cityToAdminCityDto(City city);
}
