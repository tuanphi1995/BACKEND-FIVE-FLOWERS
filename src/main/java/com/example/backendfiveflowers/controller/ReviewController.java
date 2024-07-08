package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Review;
import com.example.backendfiveflowers.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Review updateReview(@PathVariable Integer id, @RequestBody Review review) {
        review.setReviewId(id);
        return reviewService.updateReview(id, review);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public Review getReviewById(@PathVariable Integer id) {
        return reviewService.getReviewById(id).orElse(null);
    }

    @GetMapping("/all")
    public Page<Review> getAllReviews(Pageable pageable) {
        return reviewService.getAllReviews(pageable);
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviewsByProductId(@PathVariable Integer productId) {
        return reviewService.getReviewsByProductId(productId); // Thêm endpoint này
    }
}
