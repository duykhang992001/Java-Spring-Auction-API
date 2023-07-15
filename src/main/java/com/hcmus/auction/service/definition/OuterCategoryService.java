package com.hcmus.auction.service.definition;

public interface OuterCategoryService {
    void addNewOuterCategory(String categoryName);
    void updateOuterCategoryById(String outerCategoryId, String newCategoryName);
    void deleteOuterCategoryById(String outerCategoryId);
}
