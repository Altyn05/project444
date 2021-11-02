package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends ReadWriteServiceImpl<Item, Long> implements ItemService {

    private final ItemDao itemDao;

    @Autowired
    public ItemServiceImpl(ReadWriteDao<Item, Long> readWriteDao, ItemDao itemDao) {
        super(readWriteDao);
        this.itemDao = itemDao;
    }

    @Override
    public Item findById(Long id) {
        return itemDao.findById(id);
    }

    @Override
    public Item findByName(String name) {
        return itemDao.findByName(name);
    }
}
