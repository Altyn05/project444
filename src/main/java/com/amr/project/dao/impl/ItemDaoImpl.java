package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Item> findPagedItemsByCategoryId(Long categoryId, Pageable pageable) {
        long size = (long) em.createQuery(
                        "SELECT COUNT(i.id) FROM Item i JOIN i.categories c where c.id = :id")
                .setParameter("id", categoryId)
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Item> list = em.createQuery(
                        "SELECT i FROM Item i JOIN i.categories c where c.id = :id"
                        , Item.class)
                .setParameter("id", categoryId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public List<Item> findPopularItems() {
        return em.createQuery("Select u from Item u order by u.rating DESC", Item.class)
                .setMaxResults(4)
                .getResultList();
    }

    @Override
    public Page<Item> findPagedPopularItems(Pageable pageable) {
        long size = (long) em.createQuery("SELECT COUNT(i.id) FROM Item i")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Item> list = em.createQuery(
                        "Select i from Item i order by i.rating DESC", Item.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public List<Item> searchItems(String search) {
        return em.createQuery("Select u from Item u where u.name LIKE :param", Item.class)
                .setParameter("param", "%" + search + "%")
                .getResultList();
    }

    @Override
    public Page<Item> searchPagedItems(String search, Pageable pageable) {
        long size = (long) em.createQuery(
                        "Select COUNT(i.id) from Item i where i.name LIKE :param")
                .setParameter("param", "%" + search + "%")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Item> list = em.createQuery(
                        "Select i from Item i where i.name LIKE :param", Item.class)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(list, pageable, size);
    }
}
