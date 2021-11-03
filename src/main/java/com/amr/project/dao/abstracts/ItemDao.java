package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;

import java.util.List;

public interface ItemDao extends ReadWriteDao<Item, Long> {
    Item findById(Long id);
    Item findByName(String name);
    List<Item> findItemsByCategoryId(Long categoryId);
    List<Item> findPopularItems();
    List<Item> searchItems(String search);
}
