package com.example.backendfiveflowers.controller;

import com.example.backendfiveflowers.entity.Reviews;
import com.example.backendfiveflowers.exception.ResourceNotFoundException;
import com.example.backendfiveflowers.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @GetMapping
    public Page<Reviews> getAllReviews(Pageable pageable) {
        return reviewsService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Reviews> getReviewById(@PathVariable Long id) {
        return reviewsService.findById(id);
    }

    @PostMapping
    public Reviews createReview(@RequestBody Reviews reviews) {
        return reviewsService.save(reviews);
    }

    @PutMapping("/{id}")
    public Reviews updateReview(@PathVariable Long id, @RequestBody Reviews reviewDetails) {
        Reviews reviews = reviewsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        reviews.setProduct(reviewDetails.getProduct());
        reviews.setUser(reviewDetails.getUser());
        reviews.setRating(reviewDetails.getRating());
        reviews.setComment(reviewDetails.getComment());
        return reviewsService.save(reviews);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewsService.deleteById(id);
    }
}
