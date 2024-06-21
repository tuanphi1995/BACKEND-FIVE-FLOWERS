package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Product;
import com.example.backendfiveflowers.entity.Review;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.ProductRepository;
import com.example.backendfiveflowers.repository.ReviewRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ProductRepository productRepository;

    public Review addReview(Review review) {
        validateReviewEntities(review);
        return reviewRepository.save(review);
    }

    public Review updateReview(Integer id, Review review) {
        if (!id.equals(review.getReviewId())) {
            throw new IllegalArgumentException("Review ID in the path does not match the Review ID in the request body");
        }
        validateReviewEntities(review);
        return reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    public Optional<Review> getReviewById(Integer id) {
        return reviewRepository.findById(id).map(this::mapReviewEntities);
    }

    public Page<Review> getAllReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAll(pageable);
        reviews.forEach(this::mapReviewEntities);
        return reviews;
    }

    private void validateReviewEntities(Review review) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(review.getUser().getId());
        if (!userInfoOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Optional<Product> productOptional = productRepository.findById(review.getProduct().getProductId());
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        review.setUser(userInfoOptional.get());
        review.setProduct(productOptional.get());
    }

    private Review mapReviewEntities(Review review) {
        review.setUser(userInfoRepository.findById(review.getUser().getId()).orElse(null));
        review.setProduct(productRepository.findById(review.getProduct().getProductId()).orElse(null));
        return review;
    }
}
