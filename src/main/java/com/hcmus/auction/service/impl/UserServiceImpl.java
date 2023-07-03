package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.FavoriteProductDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.User;
import com.hcmus.auction.model.mapper.UserMapper;
import com.hcmus.auction.repository.UserRepository;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements GenericService<UserDTO, String>, UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FavoriteProductServiceImpl favoriteProductService;
    private final RoleHistoryServiceImpl roleHistoryService;
    private final ProductServiceImpl productService;

    @Override
    public UserDTO getById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(userMapper::toDTO).orElse(null);
    }

    @Override
    public Page<FavoriteProductDTO> getFavoriteProductsByUserId(String userId, Integer page, Integer size, Integer lte, Integer gte) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return favoriteProductService.getFavoriteProductsByUserId(userId, page, size, lte, gte);
    }

    @Override
    public Page<ProductDTO> getAuctioningProductsByUserId(String userId, Integer page, Integer size) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return productService.getAuctioningProductsByUserId(userId, page, size);
    }

    @Override
    public Page<ProductDTO> getWonProductsByUserId(String userId, Integer page, Integer size) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return productService.getWonProductsByUserId(userId, page, size);
    }

    @Override
    public void addNewFavoriteProduct(String userId, String productId) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        favoriteProductService.addNewFavoriteProduct(userId, productId);
    }

    @Override
    public void deleteFavoriteProduct(String userId, String productId) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        favoriteProductService.deleteFavoriteProduct(userId, productId);
    }

    @Override
    public void addNewRoleHistory(String userId) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        roleHistoryService.addNewRoleHistory(userId);
    }
}
