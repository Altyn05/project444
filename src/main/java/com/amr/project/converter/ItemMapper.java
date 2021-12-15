package com.amr.project.converter;

import com.amr.project.model.dto.*;
import com.amr.project.model.entity.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    List<ItemMainPageDTO> itemListToListItemMainPageDTO(List<Item> list);
    List<AdminItemDto> itemListToListAdminItemDto(List<Item> list);
    AdminItemDto itemToAdminItemDto(Item item);

    ModeratorItemDto itemToModeratorItemDto(Item item);
    List<ModeratorItemDto>  itemListToListModeratorItemDto(List<Item> list);
    ItemMainPageDTO itemToItemMainPageDTO(Item item);

    ItemDto toDto(Item item);
    Item toModel(ItemDto itemDto);
    List<ItemDto> toListDto(List<Item> list);

}