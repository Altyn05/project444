package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;

import java.util.List;

public interface ModeratorService {
    List<Shop> getNotModeratedShops();
    void rejectShop(Long id, String rejectReason);
    void approveShop(Long id);

    List<Item> getNotModeratedItems();
    void rejectItem(Long id, String rejectReason);
    void approveItem(Long id);
}
