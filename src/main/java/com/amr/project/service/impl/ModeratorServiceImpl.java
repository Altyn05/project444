package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ModeratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    private final ShopDao shopDao;
    private final ItemDao itemDao;
    public ModeratorServiceImpl(ShopDao shopDao, ItemDao itemDao) {
        this.shopDao = shopDao;
        this.itemDao = itemDao;
    }

    @Override
    public List<Shop> getNotModeratedShops() {
        return shopDao.getNotModeratedShops();
    }

    @Override
    public void rejectShop(Long id, String rejectReason) {
         Shop shop = shopDao.findById(id);
         shop.setModerated(true);
         shop.setModerateAccept(false);
         shop.setModeratedRejectReason(rejectReason);
         shopDao.update(shop);
    }

    @Override
    public void approveShop(Long id) {
        Shop shop = shopDao.findById(id);
        shop.setModerated(true);
        shop.setModerateAccept(true);
        shopDao.update(shop);
    }

    @Override
    public List<Item> getNotModeratedItems() {
        return itemDao.getNotModeratedItems();
    }

    @Override
    public void rejectItem(Long id, String rejectReason) {
        Item item = itemDao.findById(id);
        item.setModerated(true);
        item.setModerateAccept(false);
        item.setModeratedRejectReason(rejectReason);
        itemDao.update(item);
    }

    @Override
    public void approveItem(Long id) {
        Item item = itemDao.findById(id);
        item.setModerated(true);
        item.setModerateAccept(true);
        itemDao.update(item);
    }
}
