package com.amr.project.util;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.repository.ItemRepository;
import com.amr.project.service.abstracts.ReadWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Transactional
public class ShopItemsUtil {
    private final ReadWriteService<Shop, Long> rwShop;
    private final ReadWriteService<Item, Long> rwItem;
    private final ShopMapper shopMapper;

    public ShopDto updateShop(ShopDto shopDto) {
        Shop shopDB = rwShop.getByKey(Shop.class, shopDto.getId()).orElse(null);
        Item newItem = new Item();
        shopDB.clearItems();
        for(ItemDto item : shopDto.getItems()) {
            newItem = rwItem.getByKey(Item.class, item.getId()).orElse(null);
            newItem.setCount(item.getCount());
            shopDB.addItem(newItem);
            rwItem.update(newItem);
        }
        rwShop.update(shopDB);
        return shopMapper.shopToDto(shopDB);
    }
}
