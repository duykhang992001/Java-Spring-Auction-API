package com.hcmus.auction.controller.definition;

import com.hcmus.auction.common.variable.request.AuctionRequest;
import com.hcmus.auction.common.variable.request.DescriptionHistoryRequest;
import com.hcmus.auction.common.variable.request.ProductRequest;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.AuctionRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ProductController {
    ResponseEntity<Page<AuctionHistoryDTO>> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy);
    ResponseEntity<SuccessResponse> addNewProductDescription(String productId, DescriptionHistoryRequest descriptionHistoryRequest);
    ResponseEntity<SuccessResponse> addNewProduct(ProductRequest product);
    ResponseEntity<SuccessResponse> deleteProductById(String productId);
    ResponseEntity<SuccessResponse> auctionProduct(String productId, AuctionRequest auctionRequest);
    ResponseEntity<Page<AuctionRequestDTO>> getUnacceptedAuctionRequestsByProductId(String productId, Integer page, Integer size);
    ResponseEntity<SuccessResponse> acceptAuctionRequest(String requestId);
    ResponseEntity<SuccessResponse> declineAuctionRequest(String requestId);
}
