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
    List<CategoryMainPageDto> category;
    String items;
    String shops;
    long itemsCount;
    long shopsCount;
    int totalItemPages;
    int totalShopPages;
    int itemPage;
    int shopPage;
    
}
