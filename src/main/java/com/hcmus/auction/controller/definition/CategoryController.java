package com.hcmus.auction.controller.definition;

import com.hcmus.auction.common.variable.request.CategoryRequest;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CategoryController {
    ResponseEntity<Page<ProductDTO>> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size,
                                                             Integer lte, Integer gte);
    ResponseEntity<SuccessResponse> addNewInnerCategory(String outerCategoryId, CategoryRequest categoryRequest);
    ResponseEntity<SuccessResponse> addNewOuterCategory(CategoryRequest categoryRequest);
    ResponseEntity<SuccessResponse> updateInnerCategoryById(String innerCategoryId, CategoryRequest categoryRequest);
    ResponseEntity<SuccessResponse> updateOuterCategoryById(String outerCategoryId, CategoryRequest categoryRequest);
    ResponseEntity<SuccessResponse> deleteInnerCategoryById(String innerCategoryId);
    ResponseEntity<SuccessResponse> deleteOuterCategoryById(String outerCategoryId);
}
