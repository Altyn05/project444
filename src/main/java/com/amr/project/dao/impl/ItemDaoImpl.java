package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDaoImpl extends ReadWriteDaoImpl<Item,Long> implements ItemDao {
    @Override
    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    @Override
    public Item findByName(String name) {
        return em.createQuery("select i from Item i where i.name=:name", Item.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Item> findItemsByCategoryId(Long categoryId) {
        return em.createQuery("SELECT u FROM Item u JOIN u.categories i where i.id = :id", Item.class)
                .setParameter("id", categoryId).getResultList();
    }

    @Override
    public List<Item> findPopularItems() {
        return em.createQuery("Select u from Item u order by u.rating DESC", Item.class)
                .setMaxResults(4)
                .getResultList();
    }

    @Override
    public List<Item> searchItems(String search) {
        return em.createQuery("Select u from Item u where u.name LIKE :param", Item.class)
                .setParameter("param", "%" + search + "%")
                .getResultList();
    }
}
