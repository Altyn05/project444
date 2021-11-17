package com.amr.project.service.impl;

import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl extends ReadWriteServiceImpl<Shop, Long> implements ShopService {

    private final ShopDao shopDao;
    private final ShopMapper shopMapper = Mappers.getMapper(ShopMapper.class);

    @Autowired
    public ShopServiceImpl(ReadWriteDao<Shop, Long> readWriteDao, ShopDao shopDao) {
        super(readWriteDao);
        this.shopDao = shopDao;
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
    public ShopDto getShop(Long id) {
        return shopMapper.shopToDto(shopDao.getByKey(Shop.class, id).orElse(new Shop()));
    }
}
