package com.amr.project.service.impl;

import com.amr.project.converter.CategoryMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ShowMainPageDTO;
import com.amr.project.model.entity.Category;
import com.amr.project.service.abstracts.ShowMainPageService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShowMainPageServiceImpl implements ShowMainPageService {

    private final ItemDao itemDao;
    private final ShopDao shopDao;
    private final CategoryDao categoryDao;
    private final ShopMapper shopMapper = Mappers.getMapper(ShopMapper.class);
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);



    @Autowired
    public ShowMainPageServiceImpl(ItemDao itemDao, ShopDao shopDao,
                                   CategoryDao categoryDao) {
        this.itemDao = itemDao;
        this.shopDao = shopDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public ShowMainPageDTO showSearch(String s) {

        return new ShowMainPageDTO(
                shopMapper.shopListToListShopMainPageDTO(shopDao.searchShops(s)),
                itemMapper.itemListToListItemMainPageDTO(itemDao.searchItems(s)),
                categoryMapper.categoryListToListCategoryDTO(categoryDao.getAll(Category.class)),
                "Поиск товаров",
                "Поиск магазинов"
        );
    }

    @Override
    public ShowMainPageDTO findItemsByCategory(Long categoryId) {
        return new ShowMainPageDTO(
                shopMapper.shopListToListShopMainPageDTO(shopDao.findPopularShops()),
                itemMapper.itemListToListItemMainPageDTO(itemDao.findItemsByCategoryId(categoryId)),
                categoryMapper.categoryListToListCategoryDTO(categoryDao.getAll(Category.class)),
                "Подборка популярных товаров по категориям",
                "Подборка популярных магазинов"
        );
    }


    @Override
    public ShowMainPageDTO show() {
        return new ShowMainPageDTO(
                shopMapper.shopListToListShopMainPageDTO(shopDao.findPopularShops()),
                itemMapper.itemListToListItemMainPageDTO(itemDao.findPopularItems()),
                categoryMapper.categoryListToListCategoryDTO(categoryDao.getAll(Category.class)),
                "Подборка популярных товаров",
                "Подборка популярных магазинов"
        );
    }
}
