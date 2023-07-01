package com.hcmus.auction.controller.definition;

import com.hcmus.auction.common.variable.FavoriteProductRequest;
import com.hcmus.auction.common.variable.SuccessResponse;
import com.hcmus.auction.model.dto.FavoriteProductDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<Page<FavoriteProductDTO>> getFavoriteProductsByUserId(String userId, Integer page, Integer size, Integer lte, Integer gte);
    ResponseEntity<Page<ProductDTO>> getAuctioningProductsByUserId(String userId, Integer page, Integer size);
    ResponseEntity<SuccessResponse> addNewFavoriteProduct(String userId, FavoriteProductRequest favoriteProductRequest);
    ResponseEntity<SuccessResponse> deleteFavoriteProduct(String userId, FavoriteProductRequest favoriteProductRequest);
    ResponseEntity<SuccessResponse> sendRequestToUpgradeRole(String userId);
}
