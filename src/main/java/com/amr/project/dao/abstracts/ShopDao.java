package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopDao extends ReadWriteDao<Shop,Long> {
    Shop findById(Long id);
    Shop findByName(String name);


    boolean findByDataShop(Shop shop);

    List<Shop> findPopularShops();
    List<Shop> findShopsByCategoryId(Long categoryId);
    List<Shop> searchShops(String search);

    Page<Shop> findPagedPopularShops(Pageable pageable);
    Page<Shop> findPagedShopsByCategoryId(Long categoryId, Pageable pageable);
    Page<Shop> searchPagedShops(String search, Pageable pageable);
    Page<Shop> searchPagedShopsByCategoryId(String search, Long categoryId, Pageable pageable);
    List<Shop> getNotModeratedShops();
}
