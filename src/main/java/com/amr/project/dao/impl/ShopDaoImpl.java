package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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
    public boolean findByDataShop(Shop shop) {
        Country country = shop.getLocation();
        City city = shop.getCity();
        String phone = shop.getPhone();
        String email = shop.getEmail();
        String name = shop.getName();
        List<Shop> shopList = (List<Shop>) em.createQuery("select c from Shop c where c.location = :country" +
                " and c.city = :city and c.phone =:phone " +
                "and c.email = :email and c.name =:name", Shop.class)
                .setParameter("country", country)
                .setParameter("city", city)
                .setParameter("phone", phone)
                .setParameter("email", email)
                .setParameter("name", name).getResultList();
        return shopList.size() == 0;}
    @Override
    public List<Shop> findPopularShops() {
        return em.createQuery("SELECT s FROM Shop s ORDER BY s.rating DESC", Shop.class)
                .getResultList().stream().filter(Shop::isModerateAccept).collect(Collectors.toList());
    }

    @Override
    public Page<Shop> findPagedPopularShops(Pageable pageable) {
        long size = (long) em.createQuery("Select COUNT(s) FROM Shop s")
                .getSingleResult();

        pageable = pageCheck(size, pageable);

        List<Shop> list = em.createQuery("SELECT s FROM Shop s ORDER BY s.rating DESC", Shop.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList().stream().filter(Shop::isModerateAccept).collect(Collectors.toList());
        System.out.println(list);
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

    @Override
    public List<Shop> getNotModeratedShops() {
        return em.createQuery("select sh from Shop sh where sh.isModerated = false", Shop.class)
                .getResultList();
    }

}
