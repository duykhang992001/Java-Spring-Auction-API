package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.FavoriteProductDTO;
import com.hcmus.auction.model.entity.FavoriteProduct;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FavoriteProductMapper implements GenericMapper<FavoriteProduct, FavoriteProductDTO> {
    @Override
    public FavoriteProduct toEntity(FavoriteProductDTO favoriteProductDTO) {
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        User user = new User();
        Product product = new Product();

        user.setId(favoriteProductDTO.getUserId());
        product.setId(favoriteProductDTO.getProduct().getId());

        favoriteProduct.setId(favoriteProductDTO.getId());
        favoriteProduct.setCreatedAt(favoriteProductDTO.getCreatedAt());
        favoriteProduct.setUser(user);
        favoriteProduct.setProduct(product);

        return favoriteProduct;
    }

    @Override
    public FavoriteProductDTO toDTO(FavoriteProduct favoriteProduct) {
        return null;
    }
}
