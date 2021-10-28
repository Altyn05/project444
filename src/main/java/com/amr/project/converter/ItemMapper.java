package com.amr.project.converter;

import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.entity.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    List<ItemMainPageDTO> itemListToListItemMainPageDTO(List<Item> list);
    ItemMainPageDTO itemToItemMainPageDTO(Item item);
}