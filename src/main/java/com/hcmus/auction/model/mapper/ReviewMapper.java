package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.ReviewDTO;
import com.hcmus.auction.model.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements GenericMapper<Review, ReviewDTO> {
    @Override
    public Review toEntity(ReviewDTO reviewDTO) {
        return null;
    }

    @Override
    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        UserMapper userMapper = new UserMapper();
        ProductMapper productMapper = new ProductMapper();

        reviewDTO.setId(review.getId());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setCreatedAt(review.getCreatedAt());
        reviewDTO.setIsLiked(review.getIsLiked());
        reviewDTO.setRecipientId(review.getRecipient().getId());
        reviewDTO.setProduct(productMapper.toDTO(review.getProduct()));
        reviewDTO.setSender(userMapper.toDTO(review.getSender()));

        return reviewDTO;
    }
}
