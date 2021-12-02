package com.amr.project.converter;

import com.amr.project.model.dto.AddressDto;
import com.amr.project.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "city", source = "city.name")
    @Mapping(target = "country", source = "country.name")
    AddressDto toDto(Address address);
    @Mapping(target = "city.name", source = "city")
    @Mapping(target = "country.name", source = "country")
    Address toModel(AddressDto addressDto);
}
