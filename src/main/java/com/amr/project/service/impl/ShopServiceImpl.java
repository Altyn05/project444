package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ShopMainPageDTO;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl
        extends ReadWriteServiceImpl<Shop, Long>
        implements ShopService {

    private final ShopDao shopDao;
    private final EmailUtil emailUtil;
    private final ShopMapper shopMapper;

    @Autowired
    public ShopServiceImpl(
            ReadWriteDao<Shop, Long> readWriteDao,
            ShopDao shopDao,
            ShopMapper shopMapper,
            EmailUtil emailUtil
    ) {
        super(readWriteDao);
        this.shopDao = shopDao;
        this.shopMapper = shopMapper;
        this.emailUtil = emailUtil;
    }

    @Override
    public void persist(Shop shop) {
        shopDao.persist(shop);
        emailUtil.sendMessage(
                shop.getEmail(),
                "Создание магазина",
                "Ваш магазин " + shop.getName() + " был создан."
        );
    }

    @Override
    public void update(Shop shop) {
        shopDao.update(shop);
        emailUtil.sendMessage(
                shop.getEmail(),
                "Редактирование магазина",
                "Информация о Вашем магазине " + shop.getName() + " была изменена."
        );
    }

    @Override
    public void delete(Shop shop) {
        shopDao.delete(shop);
        emailUtil.sendMessage(
                shop.getEmail(),
                "Удаление магазина",
                "Ваш магазин " + shop.getName() + " был удалён."
        );
    }

    @Override
    public void deleteByKeyCascadeEnable(Class<Shop> clazz, Long aLong) {
        Shop shop = shopDao.findById(aLong);
        shopDao.deleteByKeyCascadeEnable(clazz, aLong);
        emailUtil.sendMessage(
                shop.getEmail(),
                "Удаление магазина",
                "Ваш магазин " + shop.getName() + " был удалён."
        );
    }

    @Override
    public void deleteByKeyCascadeIgnore(Class<Shop> clazz, Long aLong) {
        Shop shop = shopDao.findById(aLong);
        shopDao.deleteByKeyCascadeIgnore(clazz, aLong);
        emailUtil.sendMessage(
                shop.getEmail(),
                "Удаление магазина",
                "Ваш магазин " + shop.getName() + " был удалён."
        );
    }

    @Override
    public Shop findById(Long id) {
        return shopDao.findById(id);
    }

    @Override
    public Shop findByName(String name) {
        return shopDao.findByName(name);
    }

    @Override
    public Page<ShopMainPageDTO> findPagedPopularShops(Pageable pageable) {
        return shopPageConverter(shopDao.findPagedPopularShops(pageable));
    }

    @Override
    public Page<ShopMainPageDTO> findPagedShopsByCategoryId(
            Long categoryId,
            Pageable pageable
    ) {
        return shopPageConverter(shopDao
                .findPagedShopsByCategoryId(categoryId, pageable));
    }

    @Override
    public Page<ShopMainPageDTO> searchPagedShops(
            String search,
            Pageable pageable
    ) {
        return shopPageConverter(shopDao.searchPagedShops(search, pageable));
    }

    @Override
    public Page<ShopMainPageDTO> searchPagedShopsByCategoryId(
            String search,
            Long categoryId,
            Pageable pageable
    ) {
        return shopPageConverter(shopDao
                .searchPagedShopsByCategoryId(search, categoryId, pageable));
    }

    private Page<ShopMainPageDTO> shopPageConverter(Page<Shop> page) {
        return page.map(shopMapper::shopToShopMainPageDTO);
    }
}
