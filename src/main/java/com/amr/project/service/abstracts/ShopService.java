package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.dto.ShopMainPageDTO;
import com.amr.project.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopService extends ReadWriteService<Shop,Long> {
    Shop findById(Long id);
    Shop findByName(String name);
    public ShopDto getShop(Long id);

    Page<ShopMainPageDTO> findPagedPopularShops(Pageable pageable);
    Page<ShopMainPageDTO> findPagedShopsByCategoryId(Long categoryId, Pageable pageable);
    Page<ShopMainPageDTO> searchPagedShops(String search, Pageable pageable);

    void addNewShop(Shop shop);

    Page<ShopMainPageDTO> searchPagedShopsByCategoryId(String search, Long categoryId, Pageable pageable);

    void deleteUserShop(Shop shopDb);

    List<Shop> getNotModeratedShops();
    Shop rejectShop(Long id, String rejectReason);
    Shop approveShop(Long id);


}
