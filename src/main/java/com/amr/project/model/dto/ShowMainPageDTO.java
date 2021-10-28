package com.amr.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowMainPageDTO {
    Page<ShopMainPageDTO> popularShop;
    Page<ItemMainPageDTO> popularItem;
    List<CategoryMainPageDto> category;
    String items;
    String shops;
}
