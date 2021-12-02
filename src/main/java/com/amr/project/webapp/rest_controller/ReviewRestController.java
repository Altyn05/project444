package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewRestController {
    private ReviewService reviewService;
    private ReviewMapper reviewMapper;

    public ReviewRestController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @PutMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody Review review){
        reviewService.addReview(review);
        return new ResponseEntity<>(reviewMapper.reviewToReviewDto(review), HttpStatus.CREATED);
    }
}
