package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopDaoImpl extends ReadWriteDaoImpl<Shop,Long> implements ShopDao {
    @Override
    public Shop findById(Long id) {
        return em.find(Shop.class, id);
    }

    @Override
    public Shop findByName(String name) {
        return em.createQuery("SELECT s FROM Shop s WHERE s.name = :name", Shop.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Shop> findPopularShops() {
        return em.createQuery("SELECT s FROM Shop s ORDER BY s.rating DESC", Shop.class)
                .getResultList();
    }

    @Override
    public Page<Shop> findPagedPopularShops(Pageable pageable) {
        long size = (long) em.createQuery("Select COUNT(s) FROM Shop s")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Shop> list = em.createQuery("SELECT s FROM Shop s ORDER BY s.rating DESC", Shop.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public List<Shop> findShopsByCategoryId(Long categoryId) {
        return em.createQuery("SELECT s FROM Shop s JOIN s.items i JOIN i.categories c WHERE c.id = :id", Shop.class)
                .setParameter("id", categoryId)
                .getResultList();
    }

    @Override
    public Page<Shop> findPagedShopsByCategoryId(Long categoryId, Pageable pageable) {
        long size = (long) em.createQuery("SELECT COUNT(s) FROM Shop s JOIN s.items i JOIN i.categories c WHERE c.id = :id")
                .setParameter("id", categoryId)
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Shop> list = em.createQuery("SELECT s FROM Shop s JOIN s.items i JOIN i.categories c WHERE c.id = :id", Shop.class)
                .setParameter("id", categoryId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public List<Shop> searchShops(String search) {
        return em.createQuery("SELECT s FROM Shop s WHERE s.name LIKE :param", Shop.class)
                .setParameter("param", "%" + search + "%")
                .getResultList();
    }

    @Override
    public Page<Shop> searchPagedShops(String search, Pageable pageable) {
        long size = (long) em.createQuery("SELECT COUNT(s) FROM Shop s WHERE s.name LIKE :param")
                .setParameter("param", "%" + search + "%")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Shop> list = em.createQuery("SELECT s FROM Shop s WHERE s.name LIKE :param", Shop.class)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public Page<Shop> searchPagedShopsByCategoryId(String search, Long categoryId, Pageable pageable) {
        long size = (long) em.createQuery("SELECT COUNT(s) FROM Shop s JOIN s.items i JOIN i.categories c WHERE c.id = :id AND s.name LIKE :param")
                .setParameter("id", categoryId)
                .setParameter("param", "%" + search + "%")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Shop> list = em.createQuery("SELECT s FROM Shop s JOIN s.items i JOIN i.categories c WHERE c.id = :id AND s.name LIKE :param", Shop.class)
                .setParameter("id", categoryId)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }
}
