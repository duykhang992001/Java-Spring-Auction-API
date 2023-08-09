package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.ReviewDTO;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.entity.Review;
import com.hcmus.auction.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements GenericMapper<Review, ReviewDTO> {
    @Override
    public Review toEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        Product product = new Product();
        User sender = new User(), recipient = new User();

        product.setId(reviewDTO.getProduct().getId());
        sender.setId(reviewDTO.getSender().getId());
        recipient.setId(reviewDTO.getRecipientId());
        review.setId(reviewDTO.getId());
        review.setComment(reviewDTO.getComment());
        review.setCreatedAt(reviewDTO.getCreatedAt());
        review.setIsLiked(reviewDTO.getIsLiked());
        review.setProduct(product);
        review.setSender(sender);
        review.setRecipient(recipient);

        return review;
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
