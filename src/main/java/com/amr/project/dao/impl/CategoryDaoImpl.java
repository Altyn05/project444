package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.model.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl extends ReadWriteDaoImpl<Category, Long> implements CategoryDao {
    @Override
    public Category findById(Long id) {
        return em.find(Category.class, id);
    }

    @Override
    public Category findByName(String name) {
        return em.createQuery("select c from Category c where c.name=:name", Category.class)
                .setParameter("name", name).getSingleResult();
    }
}
