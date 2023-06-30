package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.FavoriteProductDTO;
import org.springframework.data.domain.Page;

public interface FavoriteProductService {
    Page<FavoriteProductDTO> getFavoriteProductsByUserId(String userId, Integer page, Integer size, Integer lte, Integer gte);
    void addNewFavoriteProduct(String userId, String productId);
    void deleteFavoriteProduct(String userId, String productId);
}
