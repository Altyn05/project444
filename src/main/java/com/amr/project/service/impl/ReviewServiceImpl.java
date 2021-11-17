package com.amr.project.service.impl;

import com.amr.project.model.entity.Review;
import com.amr.project.repository.ReviewRepository;
import com.amr.project.service.abstracts.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository repository;

    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addReview(Review review) {
    repository.save(review);
    }
}
