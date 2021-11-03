package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.Role;
import com.amr.project.model.entity.Shop;
import org.springframework.context.annotation.Primary;
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
    public List<Shop> searchShops(String search) {
        return em.createQuery("select sh from Shop sh where sh.name LIKE :param", Shop.class)
                .setParameter("param", "%" + search + "%")
                .getResultList();
    }
}
