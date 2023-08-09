package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    Page<ReviewDTO> getReviewsByUserId(String userId, Integer page, Integer size);
    void addNewReview(String productId, String senderId, String recipientId, String comment, Boolean isLiked);
    List<ReviewDTO> getReviewsByProductId(String productId);
}
