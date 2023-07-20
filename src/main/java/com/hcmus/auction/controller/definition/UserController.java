package com.hcmus.auction.controller.definition;

import com.hcmus.auction.common.variable.request.FavoriteProductRequest;
import com.hcmus.auction.common.variable.request.ProfileRequest;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.common.variable.response.UserPointResponse;
import com.hcmus.auction.model.dto.AccountDTO;
import com.hcmus.auction.model.dto.FavoriteProductDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.ReviewDTO;
import com.hcmus.auction.model.dto.RoleHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<Page<FavoriteProductDTO>> getFavoriteProductsByUserId(String userId, Integer page, Integer size, Integer lte, Integer gte);
    ResponseEntity<Page<ProductDTO>> getAuctioningProductsByUserId(String userId, Integer page, Integer size);
    ResponseEntity<Page<ProductDTO>> getWonProductsByUserId(String userId, Integer page, Integer size);
    ResponseEntity<Page<ProductDTO>> getExpiredOwnProductsByUserId(String userId, Integer page, Integer size);
    ResponseEntity<Page<ProductDTO>> getActiveOwnProductsByUserId(String userId, Integer page, Integer size);
    ResponseEntity<Page<RoleHistoryDTO>> getUnacceptedUpgradeRequests(Integer page, Integer size);
    ResponseEntity<SuccessResponse> addNewFavoriteProduct(String userId, FavoriteProductRequest favoriteProductRequest);
    ResponseEntity<SuccessResponse> deleteFavoriteProduct(String userId, FavoriteProductRequest favoriteProductRequest);
    ResponseEntity<SuccessResponse> sendRequestToUpgradeRole(String userId);
    ResponseEntity<Page<ReviewDTO>> getReviewsByUserId(String userId, Integer page, Integer size);
    ResponseEntity<UserPointResponse> getPointsByUserId(String userId);
    ResponseEntity<AccountDTO> getProfile(String userId);
    ResponseEntity<SuccessResponse> updateProfile(String userId, ProfileRequest newProfile);
    ResponseEntity<SuccessResponse> acceptUpgradeRequest(String requestId);
    ResponseEntity<SuccessResponse> declineUpgradeRequest(String requestId);
}
