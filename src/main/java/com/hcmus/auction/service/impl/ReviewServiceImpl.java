package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.ReviewDTO;
import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.Review;
import com.hcmus.auction.model.mapper.ReviewMapper;
import com.hcmus.auction.repository.ReviewRepository;
import com.hcmus.auction.service.definition.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public void addNewReview(String productId, String senderId, String recipientId, String comment, Boolean isLiked) {
        if (reviewRepository.existsBySenderIdAndProductId(senderId, productId))
            throw new GenericException(ErrorMessage.ALREADY_SEND_REVIEW.getMessage());

        ReviewDTO reviewDTO = new ReviewDTO();
        ProductDTO productDTO = new ProductDTO();
        UserDTO userDTO = new UserDTO();

        productDTO.setId(productId);
        userDTO.setId(senderId);
        reviewDTO.setId(UUID.randomUUID().toString());
        reviewDTO.setComment(comment);
        reviewDTO.setCreatedAt(TimeUtil.getCurrentTimestamp());
        reviewDTO.setIsLiked(isLiked);
        reviewDTO.setRecipientId(recipientId);
        reviewDTO.setProduct(productDTO);
        reviewDTO.setSender(userDTO);

        reviewRepository.save(reviewMapper.toEntity(reviewDTO));
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(String productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        return reviews.stream().map(reviewMapper::toDTO).toList();
    }
}
