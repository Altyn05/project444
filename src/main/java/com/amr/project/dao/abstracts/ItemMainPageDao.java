package com.amr.project.dao.abstracts;


import com.amr.project.model.entity.Item;

import java.util.List;

public interface ItemMainPageDao extends ReadWriteDao<Item, Long> {

    List<Item> findItemsByCategoryId(Long categoryId, int page, int itemsPerPage);
    List<Item> findPopularItems(int page, int itemsPerPage);
    List<Item> searchItems(String search, int page, int itemsPerPage);
    long itemsCount();
}

