package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopMainPageDao;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopMainPageDaoImpl extends ReadWriteDaoImpl<Shop, Long> implements ShopMainPageDao {

    @Override
    public List<Shop> findPopularShops(int page, int itemsPerPage) {
        return em.createQuery("Select u from Shop u order by u.rating DESC", Shop.class)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();
    }
    
    @Override
    public List<Shop> searchShops(String search, int page, int itemsPerPage) {
        return em.createQuery("select sh from Shop sh where sh.name LIKE :param", Shop.class)
                .setParameter("param", "%" + search + "%")
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();
    }
    
    @Override
    public long shopsCount() {
        return (long) em.createQuery("SELECT COUNT(s.id) FROM Shop s")
                .getSingleResult();
    }
}
