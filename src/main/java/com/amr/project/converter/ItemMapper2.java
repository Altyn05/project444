package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto2;
import com.amr.project.model.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper2 {
    @Mapping(source = "item.images", target = "images")
    ItemDto2 itemToItemDto(Item item);
    List<ItemDto2> itemListToListItemDto(List<Item> list);
}
