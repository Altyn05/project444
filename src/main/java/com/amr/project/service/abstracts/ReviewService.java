package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;

import java.util.List;

public interface ReviewService extends ReadWriteService<Review, Long> {
    Review findById(Long id);
    List<Review> getNotModeratedReviews();
    void rejectReview(Long id, String rejectReason);
    void approveReview(Long id);
}
