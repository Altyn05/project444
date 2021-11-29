package com.amr.project.converter;

import com.amr.project.model.dto.ShopDto2;
import com.amr.project.model.entity.Shop;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper2 {
    ShopDto2 shopToDto(Shop shop);
    List<ShopDto2> shopListToListShopDto(List<Shop> list);
}
