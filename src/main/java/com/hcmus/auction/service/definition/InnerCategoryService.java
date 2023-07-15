package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface InnerCategoryService {
    Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size, Integer lte, Integer gte);
    void addNewInnerCategory(String outerCategoryId, String categoryName);
    void updateInnerCategoryById(String innerCategoryId, String newCategoryName);
    void deleteInnerCategoryById(String innerCategoryId);
}
