package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductDTO> getProductsByCategoryId(String categoryId, Integer page, Integer size);
}
