package com.amr.project.converter;

import com.amr.project.model.dto.*;
import com.amr.project.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "user_id", source = "user.id")

    ShopDto shopToDto(Shop shop);

    Shop dtoToModel(ShopDto shopDto);

    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "user.images", target = "userImages")

    ReviewDto reviewToReviewDto(Review review);
    List<ShopMainPageDTO> shopListToListShopMainPageDTO(List<Shop> list);

    List<ShopDto> shopListToListShopDto(List<Shop> list);

    List<AdminShopDto> shopListToListAdminShopDto(List<Shop> list);

    AdminShopDto shopToAdminShopDto(Shop shop);

    ShopMainPageDTO shopToShopMainPageDTO(Shop shop);

    List<ModeratorShopDto> shopListToListModeratorShopDto (List<Shop> list);
    ModeratorShopDto shoptoModeratorShopDto (Shop shop);
}
