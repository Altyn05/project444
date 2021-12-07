package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ItemDaoImpl extends ReadWriteDaoImpl<Item,Long> implements ItemDao {
    @Override
    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    @Override
    public Item findByName(String name) {
        return em.createQuery("SELECT i FROM Item i WHERE i.name = :name", Item.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Item> findPopularItems() {
        return em.createQuery("SELECT i FROM Item i ORDER BY i.rating DESC", Item.class)
                .getResultList().stream().filter(Item::isModerateAccept).collect(Collectors.toList());
    }

    @Override
    public Page<Item> findPagedPopularItems(Pageable pageable) {
        long size = (long) em.createQuery("SELECT COUNT(i) FROM Item i")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Item> list = em.createQuery("SELECT i FROM Item i ORDER BY i.rating DESC", Item.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList().stream().filter(Item::isModerateAccept).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public List<Item> findItemsByCategoryId(Long categoryId) {
        return em.createQuery("SELECT i FROM Item i JOIN i.categories c WHERE c.id = :id", Item.class)
                .setParameter("id", categoryId)
                .getResultList();
    }

    @Override
    public Page<Item> findPagedItemsByCategoryId(Long categoryId, Pageable pageable) {
        long size = (long) em.createQuery("SELECT COUNT(i) FROM Item i JOIN i.categories c where c.id = :id")
                .setParameter("id", categoryId)
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Item> list = em.createQuery("SELECT i FROM Item i JOIN i.categories c where c.id = :id", Item.class)
                .setParameter("id", categoryId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public List<Item> searchItems(String search) {
        List<Item> list = em.createQuery("SELECT i FROM Item i WHERE i.name LIKE :param", Item.class)
                .setParameter("param", "%" + search + "%")
                .getResultList();
        return list;
    }

    @Override
    public Page<Item> searchPagedItems(String search, Pageable pageable) {
        long size = (long) em.createQuery("SELECT COUNT(i) FROM Item i WHERE i.name LIKE :param")
                .setParameter("param", "%" + search + "%")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Item> list = em.createQuery("SELECT i FROM Item i WHERE i.name LIKE :param", Item.class)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public Page<Item> searchPagedItemsByCategoryId(
            String search,
            Long categoryId,
            Pageable pageable
    ) {
        long size = (long) em.createQuery("SELECT COUNT(i) FROM Item i JOIN i.categories c WHERE c.id = :id AND i.name LIKE :param")
                .setParameter("id", categoryId)
                .setParameter("param", "%" + search + "%")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Item> list =  em.createQuery("SELECT i FROM Item i JOIN i.categories c WHERE c.id = :id AND i.name LIKE :param", Item.class)
                .setParameter("id", categoryId)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public List<Item> getNotModeratedItems() {
        return em.createQuery("Select u from Item u where u.isModerated = false", Item.class)
                .getResultList();
    }
}
