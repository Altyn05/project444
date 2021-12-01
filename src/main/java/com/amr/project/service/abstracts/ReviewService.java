package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Review;

import java.util.List;

public interface ReviewService extends ReadWriteService<Review, Long> {
    Review findById(Long id);
    List<Review> getNotModeratedReviews();
    Review rejectReview(Long id, String rejectReason);
    Review approveReview(Long id);
    void addReview(Review review);
}
