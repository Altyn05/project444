package com.amr.project.converter;

import com.amr.project.model.dto.AdminCityDto;
import com.amr.project.model.entity.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {
    List<AdminCityDto> cityListToListAdminCityDto(List<City> cities);
    AdminCityDto cityToAdminCityDto(City city);
}
