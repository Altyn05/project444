package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopMainPageDao;
import com.amr.project.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ShopMainPageDaoImpl extends ReadWriteDaoImpl<Shop, Long>
        implements ShopMainPageDao {

    @Override
    public Page<Shop> findPopularShops(Pageable pageable) {
        List<Shop> list = em.createQuery(
                "Select s from Shop s order by s.rating DESC", Shop.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        long size = (long) em.createQuery("Select COUNT(s.id) from Shop s")
                .getSingleResult();
        return new PageImpl<>(list, pageable, size);
    }
    
    @Override
    public Page<Shop> searchShops(String search, Pageable pageable) {
        List<Shop> list = em.createQuery(
                "select s from Shop s where s.name LIKE :param", Shop.class)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        long size = (long) em.createQuery(
                "select COUNT(s.id) from Shop s where s.name LIKE :param")
                .setParameter("param", "%" + search + "%")
                .getSingleResult();
        return new PageImpl<>(list, pageable, size);
    }
}
