package com.amr.project.service.impl;

import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ShopMainPageDTO;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl extends ReadWriteServiceImpl<Shop, Long> implements ShopService {

    private final ShopDao shopDao;
    private final ShopMapper shopMapper;

    @Autowired
    public ShopServiceImpl(
            ReadWriteDao<Shop, Long> readWriteDao,
            ShopDao shopDao,
            ShopMapper shopMapper
    ) {
        super(readWriteDao);
        this.shopDao = shopDao;
        this.shopMapper = shopMapper;
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

    private Page<ShopMainPageDTO> shopPageConverter(Page<Shop> page) {
        return page.map(shopMapper::shopToShopMainPageDTO);
    }
}
