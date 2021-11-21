package com.amr.project.model.dto;


import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
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

    private List<Shop> shops;

    private List<Item> items;

    private User user;
}
