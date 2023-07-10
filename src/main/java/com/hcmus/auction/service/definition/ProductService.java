package com.hcmus.auction.service.definition;

import com.hcmus.auction.common.variable.request.ProductRequest;
import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size, Integer lte, Integer gte);
    Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy);
    Page<ProductDTO> getAuctioningProductsByUserId(String userId, Integer page, Integer size);
    Page<ProductDTO> getWonProductsByUserId(String userId, Integer page, Integer size);
    Page<ProductDTO> getEndedOwnProductsByUserId(String userId, Integer page, Integer size);
    void addNewProductDescription(String productId, String content);
    void addNewProduct(ProductRequest product);
}
