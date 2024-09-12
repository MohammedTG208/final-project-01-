package com.example.jumlacycle.Service;

import com.example.jumlacycle.API.ApiException;

import com.example.jumlacycle.Model.Customer;
import com.example.jumlacycle.Model.Order;
import com.example.jumlacycle.Model.Review;
import com.example.jumlacycle.Repository.CustomerRepository;
import com.example.jumlacycle.Repository.OrderRepository;
import com.example.jumlacycle.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public List<Review> findAllReviewsByOrderId() {
        return reviewRepository.findAll();
    }

    public void addReviewForProduct(Integer orderId, Review review, Integer userId) {
        Order customerOrder=orderRepository.findByOrderIdAndCustomerId(orderId, userId);
        Order facilityOrder=orderRepository.findOrderByIdAndFacilityId(orderId,userId);
        if (customerOrder!=null){
            review.setOrder(customerOrder);
            reviewRepository.save(review);
        }else if (facilityOrder!=null){
            review.setOrder(facilityOrder);
            reviewRepository.save(review);
        }else {
            throw new ApiException("Failed to add review");
        }
    }

    public void updateReviewForProduct(Integer orderId,Integer reviewId, Review review, Integer userId) {
        Order customerOrder=orderRepository.findByOrderIdAndCustomerId(orderId, userId);
        Order facilityOrder=orderRepository.findOrderByIdAndFacilityId(orderId,userId);
        if (customerOrder!=null) {
            Review oldReview = reviewRepository.findReviewById(reviewId);
            oldReview.setOrder(customerOrder);
            oldReview.setRate(review.getRate());
            oldReview.setDescription(review.getDescription());
            reviewRepository.save(oldReview);
        }else if (facilityOrder!=null) {
            Review oldReview = reviewRepository.findReviewById(reviewId);
            oldReview.setOrder(facilityOrder);
            oldReview.setRate(review.getRate());
            oldReview.setDescription(review.getDescription());
            reviewRepository.save(oldReview);
        }else {
            throw new ApiException("Failed to update review");
        }
    }


    public void deleteReviewForProduct(Integer orderId,Integer reviewId, Integer userId) {
       Order customerOrder=orderRepository.findByOrderIdAndCustomerId(orderId, userId);
       Order facilityOrder=orderRepository.findOrderByIdAndFacilityId(orderId,userId);
       if (customerOrder!=null) {
           reviewRepository.deleteReviewByIdAndOrderId(reviewId,customerOrder.getId());
       }else if (facilityOrder!=null) {
           reviewRepository.deleteReviewByIdAndOrderId(reviewId,facilityOrder.getId());
       }else {
           throw new ApiException("Failed to delete review");
       }
    }
}
