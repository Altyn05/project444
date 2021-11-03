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
        return em.createQuery("select u from Shop u where u.name=:name", Shop.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Shop> findPopularShops() {
        return em.createQuery("Select u from Shop u order by u.rating DESC", Shop.class)
                .setMaxResults(6)
                .getResultList();
    }

    @Override
    public Page<Shop> findPagedPopularShops(Pageable pageable) {
        long size = (long) em.createQuery("Select COUNT(sh.id) from Shop sh")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Shop> list = em.createQuery(
                        "Select sh from Shop sh order by sh.rating DESC",
                        Shop.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }

    @Override
    public List<Shop> searchShops(String search) {
        return em.createQuery("select sh from Shop sh where sh.name LIKE :param", Shop.class)
                .setParameter("param", "%" + search + "%")
                .getResultList();
    }

    @Override
    public Page<Shop> searchPagedShops(String search, Pageable pageable) {
        long size = (long) em.createQuery(
                        "select COUNT(sh.id) from Shop sh where sh.name LIKE :param")
                .setParameter("param", "%" + search + "%")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Shop> list = em.createQuery(
                        "select sh from Shop sh where sh.name LIKE :param",
                        Shop.class)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(list, pageable, size);
    }
}
