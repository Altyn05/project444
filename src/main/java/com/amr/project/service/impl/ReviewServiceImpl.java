package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ReviewDao;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl  extends ReadWriteServiceImpl<Review, Long> implements ReviewService {
    private final ReviewDao reviewDao;

    public ReviewServiceImpl(ReadWriteDao<Review, Long> readWriteDao, ReviewDao reviewDao) {
        super(readWriteDao);
        this.reviewDao = reviewDao;
    }


    @Override
    public Review findById(Long id) {
        return reviewDao.findById(id);
    }

    @Override
    public List<Review> getNotModeratedReviews() {
        return reviewDao.getNotModeratedReviews();
    }

    @Override
    public Review rejectReview(Long id, String rejectReason) {
        Review review = reviewDao.findById(id);
        review.setModerated(true);
        review.setModerateAccept(false);
        review.setModeratedRejectReason(rejectReason);
        reviewDao.update(review);
        return review;
    }

    @Override
    public Review approveReview(Long id) {
        Review review = reviewDao.findById(id);
        review.setModerated(true);
        review.setModerateAccept(true);
        reviewDao.update(review);
        return review;
    }

}
