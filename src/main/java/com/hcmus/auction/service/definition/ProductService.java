package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size);
    Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy);
}
