package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ReviewDao;
import com.amr.project.model.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl extends ReadWriteDaoImpl<Review,Long> implements ReviewDao {

    @Override
    public List<Review> getNotModeratedReviews() {
        return em.createQuery("Select u from Review u where u.isModerated = false", Review.class)
                .getResultList();
    }

    @Override
    public Review findById(Long id) {
        return em.find(Review.class, id);
    }

}
