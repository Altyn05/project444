package com.amr.project.service.impl;

import com.amr.project.converter.CategoryMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ShopDao;
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
    private final ItemDao itemDao;
    private final ShopDao shopDao;
    private final CategoryDao categoryDao;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ShowMainPageServiceImpl(
            ItemDao itemDao,
            ShopDao shopDao,
            CategoryDao categoryDao
    ) {
        this.itemDao = itemDao;
        this.shopDao = shopDao;
        this.categoryDao = categoryDao;
        shopMapper = Mappers.getMapper(ShopMapper.class);
        itemMapper = Mappers.getMapper(ItemMapper.class);
        categoryMapper = Mappers.getMapper(CategoryMapper.class);
    }

    @Override
    public ShowMainPageDTO showSearch(
            String s, Pageable itemPages, Pageable shopPages
    ) {
        return new ShowMainPageDTO(
                shopPageConverter(shopDao.searchPagedShops(s, shopPages)),
                itemPageConverter(itemDao.searchPagedItems(s, itemPages)),
                categoryMapper.categoryListToListCategoryDTO(
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
                        shopDao.findPagedShopsByCategoryId(categoryId, shopPages)),
                itemPageConverter(itemDao
                        .findPagedItemsByCategoryId(categoryId, itemPages)),
                categoryMapper.categoryListToListCategoryDTO(
                        categoryDao.getAll(Category.class)),
                "Подборка популярных товаров по категориям",
                "Подборка популярных магазинов"
        );
    }

    @Override
    public ShowMainPageDTO show(Pageable itemPages, Pageable shopPages) {
        return new ShowMainPageDTO(
                shopPageConverter(
                        shopDao.findPagedPopularShops(shopPages)),
                itemPageConverter(
                        itemDao.findPagedPopularItems(itemPages)),
                categoryMapper.categoryListToListCategoryDTO(
                        categoryDao.getAll(Category.class)),
                "Подборка популярных товаров",
                "Подборка популярных магазинов"
        );
    }

    private Page<ItemMainPageDTO> itemPageConverter(Page<Item> page) {
        return page.map(itemMapper::itemToItemMainPageDTO);
    }
    private Page<ShopMainPageDTO> shopPageConverter(Page<Shop> page) {
        return page.map(shopMapper::shopToShopMainPageDTO);
    }
}
