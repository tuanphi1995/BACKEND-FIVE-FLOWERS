package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Review;
import com.example.backendfiveflowers.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Review updateReview(@PathVariable Integer id, @RequestBody Review review) {
        if (!id.equals(review.getReviewId())) {
            throw new IllegalArgumentException("Review ID does not match");
        }
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public void deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Review getReviewById(@PathVariable Integer id) {
        return reviewService.getReviewById(id).orElse(null);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<Review> getAllReviews(Pageable pageable) {
        return reviewService.getAllReviews(pageable);
    }
}
