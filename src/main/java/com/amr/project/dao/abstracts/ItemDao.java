package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemDao extends ReadWriteDao<Item, Long> {
    Item findById(Long id);
    Item findByName(String name);
    List<Item> findItemsByCategoryId(Long categoryId);
    List<Item> findPopularItems();
    List<Item> searchItems(String search);

    Page<Item> findPagedItemsByCategoryId(Long categoryId, Pageable pageable);
    Page<Item> findPagedPopularItems(Pageable pageable);
    Page<Item> searchPagedItems(String search, Pageable pageable);
    Page<Item> searchPagedItemsByCategoryId(String search, Long categoryId, Pageable pageable);

    List<Item> getNotModeratedItems();



}
