package com.amr.project.service.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ItemServiceImpl
        extends ReadWriteServiceImpl<Item, Long>
        implements ItemService {

    private final ItemDao itemDao;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(
            ReadWriteDao<Item, Long> readWriteDao,
            ItemDao itemDao,
            ItemMapper itemMapper
    ) {
        super(readWriteDao);
        this.itemDao = itemDao;
        this.itemMapper = itemMapper;
    }

    @Override
    public Item findById(Long id) {
        return itemDao.findById(id);
    }

    @Override
    public Item findByName(String name) {
        return itemDao.findByName(name);
    }

    @Override
    public Page<ItemMainPageDTO> findPagedPopularItems(Pageable pageable) {
        return itemPageConverter(itemDao.findPagedPopularItems(pageable));
    }

    @Override
    public Page<ItemMainPageDTO> findPagedItemsByCategoryId(
            Long categoryId,
            Pageable pageable
    ) {
        return itemPageConverter(itemDao
                .findPagedItemsByCategoryId(categoryId, pageable));
    }

    @Override
    public Page<ItemMainPageDTO> searchPagedItems(
            String search,
            Pageable pageable
    ) {
        return itemPageConverter(itemDao.searchPagedItems(search, pageable));
    }

    @Override
    public Page<ItemMainPageDTO> searchPagedItemsByCategoryId(
            String search,
            Long categoryId,
            Pageable pageable
    ) {
        return itemPageConverter(itemDao
                .searchPagedItemsByCategoryId(search, categoryId, pageable));
    }

    private Page<ItemMainPageDTO> itemPageConverter(Page<Item> page) {
        return page.map(itemMapper::itemToItemMainPageDTO);
    }


    @Override
    public List<Item> getNotModeratedItems() {
        return itemDao.getNotModeratedItems();
    }

    @Override
    public Item rejectItem(Long id, String rejectReason) {
        Item item = itemDao.findById(id);
        item.setModerated(true);
        item.setModerateAccept(false);
        item.setModeratedRejectReason(rejectReason);
        itemDao.update(item);
        return item;
    }

    @Override
    public Item approveItem(Long id) {
        Item item = itemDao.findById(id);
        item.setModerated(true);
        item.setModerateAccept(true);
        itemDao.update(item);
        return item;
    }
}
