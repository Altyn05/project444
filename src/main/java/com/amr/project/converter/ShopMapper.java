package com.amr.project.converter;

import com.amr.project.model.dto.*;
import com.amr.project.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    @Mapping(target = "username", source = "user.username")
    ShopDto shopToDto(Shop shop);

    List<ShopMainPageDTO> shopListToListShopMainPageDTO(List<Shop> list);

}
