package com.amr.project.converter;

import com.amr.project.model.dto.AddressDto;
import com.amr.project.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import com.amr.project.model.dto.AdminAddressDto;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "city", source = "city.name")
    @Mapping(target = "country", source = "country.name")
    AddressDto toDto(Address address);
    @Mapping(target = "city.name", source = "city")
    @Mapping(target = "country.name", source = "country")
    Address toModel(AddressDto addressDto);

    List<AdminAddressDto> addressListToListAdminAddressDto(List<Address> addresses);
    AdminAddressDto addressToAdminAddressDto(Address address);
}
