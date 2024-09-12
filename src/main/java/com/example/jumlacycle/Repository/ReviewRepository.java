package com.example.jumlacycle.Repository;

import com.example.jumlacycle.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(Integer id);
    @Query("DELETE FROM Review r WHERE r.id = ?1 AND r.order.id = ?2")
    void deleteReviewByIdAndOrderId(Integer reviewId, Integer orderId);
}
