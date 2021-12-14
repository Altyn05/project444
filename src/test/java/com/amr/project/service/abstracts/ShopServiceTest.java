package com.amr.project.service.abstracts;

import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.Shop;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


class ShopServiceTest {

    @Autowired
    ShopService shopService;

    @MockBean
    private ShopDao shopDao;


    @Test
    void addNewShop() {
        Shop shop = new Shop();
        shopService.addNewShop(shop);
        Mockito.verify(shopDao,Mockito.atLeastOnce()).persist(shop);
    }

    @Test
    void update() {
        Shop shop = new Shop();
        shopService.update(shop);
        Mockito.verify(shopDao,Mockito.atLeastOnce()).update(shop);
    }
}