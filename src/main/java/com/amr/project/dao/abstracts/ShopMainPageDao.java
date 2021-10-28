package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopMainPageDao extends ReadWriteDao<Shop, Long> {
    Page<Shop> findPopularShops(Pageable pageable);
    Page<Shop> searchShops(String search, Pageable pageable);

}
