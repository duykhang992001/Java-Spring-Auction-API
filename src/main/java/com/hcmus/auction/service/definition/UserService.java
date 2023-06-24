package com.hcmus.auction.service.definition;

public interface UserService {
    void addNewFavoriteProduct(String userId, String productId);
    void deleteFavoriteProduct(String userId, String productId);
}
