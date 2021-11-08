package com.amr.project.service.impl;

import com.amr.project.converter.CategoryMapper;
import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.dto.CategoryDto;
import com.amr.project.model.entity.Category;
import com.amr.project.service.abstracts.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl
        extends ReadWriteServiceImpl<Category, Long>
        implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(
            ReadWriteDao<Category, Long> readWriteDao,
            CategoryDao categoryDao,
            CategoryMapper categoryMapper
    ) {
        super(readWriteDao);
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryDao.findByName(name);
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryMapper.categoryListToListCategoryDTO(
                categoryDao.getAll(Category.class));
    }
}
