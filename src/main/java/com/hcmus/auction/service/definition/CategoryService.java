package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size);
}
