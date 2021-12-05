package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewRestController {
    private ReviewService reviewService;
    private ReviewMapper reviewMapper;

    public ReviewRestController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable(value = "id", required = true) Long id) {
        if (reviewService.existsById(Review.class, id)) {
            return new ResponseEntity<>(reviewService.getByKey(Review.class, id).map(reviewMapper::reviewToReviewDto).orElse(new ReviewDto()), HttpStatus.OK);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReviewы() {
            return new ResponseEntity<>(reviewMapper.reviewListToListReviewDto(reviewService.getAll(Review.class)), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<ReviewDto> addReview(@RequestBody Review review){
        reviewService.addReview(review);
        return new ResponseEntity<>(reviewMapper.reviewToReviewDto(review), HttpStatus.CREATED);
    }
}
