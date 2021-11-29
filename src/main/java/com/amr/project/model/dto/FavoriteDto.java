package com.amr.project.model.dto;


import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavoriteDto {
    private Long id;

    private List<ShopDto> shops;
    @JsonIgnore(value = true)
    private List<ItemDto> items;
    @JsonIgnore(value = true)
    private User user;
}
