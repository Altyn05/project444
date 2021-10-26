package com.amr.project.dao.impl;


import com.amr.project.dao.abstracts.ItemMainPageDao;
import com.amr.project.model.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemMainPageDaoImpl extends ReadWriteDaoImpl<Item, Long> implements ItemMainPageDao {

    @Override
    public List<Item> findItemsByCategoryId(Long categoryId, int page, int itemsPerPage) {
        return em.createQuery("SELECT u FROM Item u JOIN u.categories i where i.id = :id", Item.class)
                .setParameter("id", categoryId)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();
    }

    @Override
    public List<Item> findPopularItems(int page, int itemsPerPage) {
        return em.createQuery("Select u from Item u order by u.rating DESC", Item.class)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();
    }

    @Override
    public List<Item> searchItems(String search, int page, int itemsPerPage) {
        return em.createQuery("Select u from Item u where u.name LIKE :param", Item.class)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();
    }
    
    @Override
    public long itemsCount() {
        return (long) em.createQuery("SELECT COUNT(i.id) FROM Item i")
                .getSingleResult();
    }
}
