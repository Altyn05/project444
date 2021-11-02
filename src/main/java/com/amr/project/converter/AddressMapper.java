package com.amr.project.converter;

import com.amr.project.model.dto.AdminAddressDto;
import com.amr.project.model.entity.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    List<AdminAddressDto> addressListToListAdminAddressDto(List<Address> addresses);
    AdminAddressDto addressToAdminAddressDto(Address address);
}
