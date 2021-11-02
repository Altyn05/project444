package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemMainPageDao extends ReadWriteDao<Item, Long> {
    Page<Item> findItemsByCategoryId(Long categoryId, Pageable pageable);
    Page<Item> findPopularItems(Pageable pageable);
    Page<Item> searchItems(String search, Pageable pageable);
}
