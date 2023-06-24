package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.User;
import com.hcmus.auction.model.mapper.UserMapper;
import com.hcmus.auction.repository.UserRepository;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements GenericService<UserDTO, String>, UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FavoriteProductServiceImpl favoriteProductService;

    @Override
    public UserDTO getById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(userMapper::toDTO).orElse(null);
    }

    @Override
    public void addNewFavoriteProduct(String userId, String productId) {
        favoriteProductService.addNewFavoriteProduct(userId, productId);
    }

    @Override
    public void deleteFavoriteProduct(String userId, String productId) {
        favoriteProductService.deleteFavoriteProduct(userId, productId);
    }
}
