package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ShopMainPageDTO;
import com.amr.project.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService extends ReadWriteService<Shop,Long> {
    Shop findById(Long id);
    Shop findByName(String name);
    Page<ShopMainPageDTO> findPagedPopularShops(Pageable pageable);
    Page<ShopMainPageDTO> findPagedShopsByCategoryId(Long categoryId, Pageable pageable);
    Page<ShopMainPageDTO> searchPagedShops(String search, Pageable pageable);
    Page<ShopMainPageDTO> searchPagedShopsByCategoryId(String search, Long categoryId, Pageable pageable);
}
