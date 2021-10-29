package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemMainPageDao;
import com.amr.project.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemMainPageDaoImpl extends ReadWriteDaoImpl<Item, Long>
        implements ItemMainPageDao {

    @Override
    public Page<Item> findItemsByCategoryId(Long categoryId, Pageable pageable) {
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
    public Page<Item> findPopularItems(Pageable pageable) {
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
    public Page<Item> searchItems(String search, Pageable pageable) {
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
