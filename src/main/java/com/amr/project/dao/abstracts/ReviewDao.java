package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDao extends ReadWriteDao<Review,Long> {
    List<Review> getNotModeratedReviews();
    Review findById(Long id);
}
