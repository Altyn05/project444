package com.amr.project.webapp.rest_controller;

import com.amr.project.model.entity.Review;
import com.amr.project.service.impl.ReviewServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewRestController {
    private ReviewServiceImpl reviewService;

    public ReviewRestController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

//    @GetMapping("/{id}")
//    public Review getReview(@PathVariable(value = "id", required = true) Long id){
//        return reviewService.
//    }

    @PutMapping
    public Review addUser(@RequestBody Review review){
        reviewService.addReview(review);
        return review;
    }
}
