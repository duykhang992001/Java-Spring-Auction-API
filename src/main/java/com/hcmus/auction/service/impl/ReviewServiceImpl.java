package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.ReviewDTO;
import com.hcmus.auction.model.entity.Review;
import com.hcmus.auction.model.mapper.ReviewMapper;
import com.hcmus.auction.repository.ReviewRepository;
import com.hcmus.auction.service.definition.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public Page<ReviewDTO> getReviewsByUserId(String userId, Integer page, Integer size) {
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<Review> reviewPage = reviewRepository.findAllByRecipientId(userId, pageable);
        return reviewPage.map(reviewMapper::toDTO);
    }
}
