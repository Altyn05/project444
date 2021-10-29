package com.amr.project.service.impl;

import com.amr.project.converter.CategoryMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.dao.abstracts.ItemMainPageDao;
import com.amr.project.dao.abstracts.ShopMainPageDao;
import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.dto.ShopMainPageDTO;
import com.amr.project.model.dto.ShowMainPageDTO;
import com.amr.project.model.entity.Category;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShowMainPageService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShowMainPageServiceImpl implements ShowMainPageService {
    private final ItemMainPageDao itemMainPageDao;
    private final ShopMainPageDao shopMainPageDao;
    private final CategoryDao categoryDao;
    private final ShopMapper shopMapper = Mappers.getMapper(ShopMapper.class);
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Autowired
    public ShowMainPageServiceImpl(
            ItemMainPageDao itemMainPageDao,
            ShopMainPageDao shopMainPageDao,
            CategoryDao categoryDao
    ) {
        this.itemMainPageDao = itemMainPageDao;
        this.shopMainPageDao = shopMainPageDao;
        this.categoryDao = categoryDao;
    }
    
    @Override
    public ShowMainPageDTO showSearch(
            String s, Pageable itemPages, Pageable shopPages
    ) {
        return new ShowMainPageDTO(
                shopPageConverter(shopMainPageDao.searchShops(s, shopPages)),
                itemPageConverter(itemMainPageDao.searchItems(s, itemPages)),
                categoryMapper.categoryListToListCategoryMainPageDTO(
                        categoryDao.getAll(Category.class)),
                "Поиск товаров",
                "Поиск магазинов"
        );
    }

    @Override
    public ShowMainPageDTO findItemsByCategory(
            Long categoryId, Pageable itemPages, Pageable shopPages
    ) {
        return new ShowMainPageDTO(
                shopPageConverter(
                        shopMainPageDao.findPopularShops(shopPages)),
                itemPageConverter(itemMainPageDao
                        .findItemsByCategoryId(categoryId, itemPages)),
                categoryMapper.categoryListToListCategoryMainPageDTO(
                        categoryDao.getAll(Category.class)),
                "Подборка популярных товаров по категориям",
                "Подборка популярных магазинов"
        );
    }

    @Override
    public ShowMainPageDTO show(Pageable itemPages, Pageable shopPages) {
        return new ShowMainPageDTO(
                shopPageConverter(
                        shopMainPageDao.findPopularShops(shopPages)),
                itemPageConverter(
                        itemMainPageDao.findPopularItems(itemPages)),
                categoryMapper.categoryListToListCategoryMainPageDTO(
                        categoryDao.getAll(Category.class)),
                "Подборка популярных товаров",
                "Подборка популярных магазинов"
        );
    }

    private Page<ItemMainPageDTO> itemPageConverter(Page<Item> page) {
        return page.map(itemMapper::itemToItemMainPageDTO);
    }
    private Page<ShopMainPageDTO> shopPageConverter(Page<Shop> page) {
        return page.map(shopMapper::shopToSHopMainPageDTO);
    }
}
