package com.amr.project.service.impl;

import com.amr.project.model.entity.Favorite;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class FavoriteImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public Favorite getFavorite(Long id) {
        TypedQuery<Favorite> query = entityManager.createQuery("select u from Favorite u where u.user.id=:id",
                Favorite.class).setParameter("id", id);
        Favorite favorite = query.getSingleResult();
        return query.getSingleResult();
    }

    public List<Favorite> getFavoriteList(Long id) {
        TypedQuery<Favorite> query = entityManager.createQuery("select u from Favorite u where u.user.id=:id",
                Favorite.class).setParameter("id", id);
        List<Favorite> favoriteList = query.getResultList();
        return query.getResultList();
    }

}
