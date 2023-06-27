package com.hcmus.auction.controller.definition;

import com.hcmus.auction.common.variable.FavoriteProductRequest;
import com.hcmus.auction.common.variable.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<SuccessResponse> addNewFavoriteProduct(String userId, FavoriteProductRequest favoriteProductRequest);
    ResponseEntity<SuccessResponse> deleteFavoriteProduct(String userId, FavoriteProductRequest favoriteProductRequest);
    ResponseEntity<SuccessResponse> sendRequestToUpgradeRole(String userId);
}
