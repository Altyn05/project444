package com.amr.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPageDto {
    List<CountryDto> countryDtoList;
    List<AdminCityDto> cityDtoList;
    List<AdminAddressDto> addressDtoList;
    List<CategoryDto> categoryDtoList;
    List<AdminUserDto> userDtoList;
    List<AdminShopDto> shopDtoList;
    List<AdminItemDto> itemDtoList;
    List<RoleDto> roleDtoList;
}
