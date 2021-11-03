package com.amr.project.converter;

import com.amr.project.model.dto.AdminItemDto;
import com.amr.project.model.dto.AdminItemShortDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.entity.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    List<ItemMainPageDTO> itemListToListItemMainPageDTO(List<Item> list);
    List<AdminItemDto> itemListToListAdminItemDto(List<Item> list);
    AdminItemDto itemToAdminItemDto(Item item);
}