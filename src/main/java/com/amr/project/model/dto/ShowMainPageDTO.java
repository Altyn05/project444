package com.amr.project.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowMainPageDTO {
    List<ShopMainPageDTO> popularShop;
    List<ItemMainPageDTO> popularItem;
    List<CategoryDto> category;
    String items;
    String shops;
}
