package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Category;

public interface CategoryDao extends ReadWriteDao<Category, Long> {
    Category findById(Long id);
    Category findByName(String name);
}
