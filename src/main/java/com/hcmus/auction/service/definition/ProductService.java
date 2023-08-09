package com.hcmus.auction.service.definition;

import com.hcmus.auction.common.variable.request.ProductRequest;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.AuctionRequestDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size, Integer lte, Integer gte);
    Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy);
    Page<ProductDTO> getAuctioningProductsByUserId(String userId, Integer page, Integer size);
    Page<ProductDTO> getWonProductsByUserId(String userId, Integer page, Integer size);
    Page<ProductDTO> getExpiredOwnProductsByUserId(String userId, Integer page, Integer size);
    Page<ProductDTO> getActiveOwnProductsByUserId(String userId, Integer page, Integer size);
    void addNewProductDescription(String productId, String content);
    void addNewProduct(ProductRequest product);
    void deleteProductById(String productId);
    SuccessResponse auctionProduct(String productId, String userId, Integer price);
    void declineAuctionHistory(String auctionId);
    Page<AuctionRequestDTO> getUnacceptedAuctionRequestsByProductId(String productId, Integer page, Integer size);
    void acceptAuctionRequest(String requestId);
    void declineAuctionRequest(String requestId);
    void reviewProductByBidder(String productId, String userId, String comment, Boolean isLiked);
    void reviewProductByOwner(String productId, String userId, String comment, Boolean isLiked);
    List<ReviewDTO> getReviewsByProductId(String productId);
}