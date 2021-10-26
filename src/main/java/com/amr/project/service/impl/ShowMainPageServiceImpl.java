package com.amr.project.service.impl;

import com.amr.project.converter.CategoryMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.dao.abstracts.ItemMainPageDao;
import com.amr.project.dao.abstracts.ShopMainPageDao;
import com.amr.project.model.dto.ItemMainPageDTO;
import com.amr.project.model.dto.ShowMainPageDTO;
import com.amr.project.model.entity.Category;
import com.amr.project.service.abstracts.ShowMainPageService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShowMainPageServiceImpl implements ShowMainPageService {

    private final ItemMainPageDao itemMainPageDao;
    private final ShopMainPageDao shopMainPageDao;
    private final CategoryDao categoryDao;
    private final ShopMapper shopMapper = Mappers.getMapper(ShopMapper.class);
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    private long itemsCount = 1;
    private long shopsCount = 1;
    private int totalItemPages = 1;
    private int totalShopPages = 1;

    @Autowired
    public ShowMainPageServiceImpl(ItemMainPageDao itemMainPageDao, ShopMainPageDao shopMainPageDao,
                                   CategoryDao categoryDao) {
        this.itemMainPageDao = itemMainPageDao;
        this.shopMainPageDao = shopMainPageDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public ShowMainPageDTO showSearch(
            String s
            , int itemPage
            , int itemsPerPage
            , int shopPage
            , int shopsPerPage
    ) {
        init(itemsPerPage, shopsPerPage);
        
        return new ShowMainPageDTO(
                shopMapper.shopListToListShopMainPageDTO(shopMainPageDao.searchShops(s, shopPage, shopsPerPage)),
                itemMapper.itemListToListItemMainPageDTO(itemMainPageDao.searchItems(s, itemPage, itemsPerPage)),
                categoryMapper.categoryListToListCategoryMainPageDTO(categoryDao.getAll(Category.class)),
                "Поиск товаров",
                "Поиск магазинов",
                itemsCount,
                shopsCount,
                totalItemPages,
                totalShopPages,
                itemPage,
                shopPage
        );
    }

    @Override
    public ShowMainPageDTO findItemsByCategory(Long categoryId
            , int itemPage
            , int itemsPerPage
            , int shopPage
            , int shopsPerPage
    ) {
        init(itemsPerPage, shopsPerPage);
        
        return new ShowMainPageDTO(
                shopMapper.shopListToListShopMainPageDTO(shopMainPageDao.findPopularShops(shopPage, shopsPerPage)),
                itemMapper.itemListToListItemMainPageDTO(itemMainPageDao.findItemsByCategoryId(categoryId, itemPage, itemsPerPage)),
                categoryMapper.categoryListToListCategoryMainPageDTO(categoryDao.getAll(Category.class)),
                "Подборка популярных товаров по категориям",
                "Подборка популярных магазинов",
                itemsCount,
                shopsCount,
                totalItemPages,
                totalShopPages,
                itemPage,
                shopPage
        );
    }


    @Override
    public ShowMainPageDTO show(
            int itemPage
            , int itemsPerPage
            , int shopPage
            , int shopsPerPage
    ) {
        init(itemsPerPage, shopsPerPage);
        
        return new ShowMainPageDTO(
                shopMapper.shopListToListShopMainPageDTO(shopMainPageDao.findPopularShops(shopPage, shopsPerPage)),
                itemMapper.itemListToListItemMainPageDTO(itemMainPageDao.findPopularItems(itemPage, itemsPerPage)),
                categoryMapper.categoryListToListCategoryMainPageDTO(categoryDao.getAll(Category.class)),
                "Подборка популярных товаров",
                "Подборка популярных магазинов",
                itemsCount,
                shopsCount,
                totalItemPages,
                totalShopPages,
                itemPage,
                shopPage
        );
    }
    
    private void init(int itemsPerPage, int shopsPerPage) {
        itemsCount = itemMainPageDao.itemsCount();
        shopsCount = shopMainPageDao.shopsCount();
        totalItemPages = (int) itemsCount / itemsPerPage + 1;
        totalShopPages = (int) shopsCount / shopsPerPage + 1;
    }
}
