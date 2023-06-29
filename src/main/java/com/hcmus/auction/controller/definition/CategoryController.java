package com.hcmus.auction.controller.definition;

import com.hcmus.auction.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CategoryController {
    ResponseEntity<Page<ProductDTO>> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size,
                                                             Integer lte, Integer gte);
}
