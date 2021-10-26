package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Shop;

import java.util.List;

public interface ShopMainPageDao extends ReadWriteDao<Shop, Long> {
    List<Shop> findPopularShops(int page, int itemsPerPage);
    List<Shop> searchShops(String search, int page, int itemsPerPage);
    long shopsCount();

}
