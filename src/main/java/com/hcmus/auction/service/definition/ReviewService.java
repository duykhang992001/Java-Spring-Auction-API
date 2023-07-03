package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.ReviewDTO;
import org.springframework.data.domain.Page;

public interface ReviewService {
    Page<ReviewDTO> getReviewsByUserId(String userId, Integer page, Integer size);
}
