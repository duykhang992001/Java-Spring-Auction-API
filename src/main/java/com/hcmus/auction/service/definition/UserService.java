package com.hcmus.auction.service.definition;

import com.hcmus.auction.common.variable.UserPointResponse;
import com.hcmus.auction.model.dto.AccountDTO;
import com.hcmus.auction.model.dto.FavoriteProductDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.ReviewDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<FavoriteProductDTO> getFavoriteProductsByUserId(String userId, Integer page, Integer size, Integer lte, Integer gte);
    Page<ProductDTO> getAuctioningProductsByUserId(String userId, Integer page, Integer size);
    Page<ProductDTO> getWonProductsByUserId(String userId, Integer page, Integer size);
    void addNewFavoriteProduct(String userId, String productId);
    void deleteFavoriteProduct(String userId, String productId);
    void addNewRoleHistory(String userId);
    Page<ReviewDTO> getReviewsByUserId(String userId, Integer page, Integer size);
    UserPointResponse getPointsByUserId(String userId);
    AccountDTO getProfile(String userId);
}