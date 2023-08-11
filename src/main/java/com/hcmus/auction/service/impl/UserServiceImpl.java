package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.common.variable.request.ProfileRequest;
import com.hcmus.auction.common.variable.response.UserPointResponse;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AccountDTO;
import com.hcmus.auction.model.dto.FavoriteProductDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.ReviewDTO;
import com.hcmus.auction.model.dto.RoleHistoryDTO;
import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.User;
import com.hcmus.auction.model.mapper.UserMapper;
import com.hcmus.auction.model.mapper.UserTypeMapper;
import com.hcmus.auction.repository.UserRepository;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements GenericService<UserDTO, String>, UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FavoriteProductServiceImpl favoriteProductService;

    @Autowired
    private RoleHistoryServiceImpl roleHistoryService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ReviewServiceImpl reviewService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private UserTypeServiceImpl userTypeService;

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
    public Page<ProductDTO> getExpiredOwnProductsByUserId(String userId, Integer page, Integer size) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return productService.getExpiredOwnProductsByUserId(userId, page, size);
    }

    @Override
    public Page<ProductDTO> getActiveOwnProductsByUserId(String userId, Integer page, Integer size) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return productService.getActiveOwnProductsByUserId(userId, page, size);
    }

    @Override
    public Page<RoleHistoryDTO> getUnacceptedUpgradeRequests(Integer page, Integer size) {
        return roleHistoryService.getUnacceptedUpgradeRequests(page, size);
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

    @Override
    public Page<ReviewDTO> getReviewsByUserId(String userId, Integer page, Integer size) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return reviewService.getReviewsByUserId(userId, page, size);
    }

    @Override
    public UserPointResponse getPointsByUserId(String userId) {
        UserDTO user = this.getById(userId);
        if (user == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return new UserPointResponse(user.getNumOfLike(), user.getNumOfDislike());
    }

    @Override
    public AccountDTO getProfile(String userId) {
        if (this.getById(userId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        return accountService.getById(userId);
    }

    @Override
    public void updateProfile(String userId, ProfileRequest newProfile) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null);

        if (user != null) {
            accountService.updateEmail(userId, newProfile.getEmail());
            user.setAddress(newProfile.getAddress());
            user.setName(newProfile.getName());
            userRepository.save(user);
        } else {
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        }

    }

    @Override
    public void acceptUpgradeRequest(String requestId) {
        final Integer TIME_STAMP_IN_7_DAYS = 7 * 24 * 60 * 60;
        final String SELLER_TYPE = "Seller";
        UserTypeMapper userTypeMapper = new UserTypeMapper();
        roleHistoryService.acceptUpgradeRequest(requestId);

        RoleHistoryDTO roleHistoryDTO = roleHistoryService.getById(requestId);
        Optional<User> userOptional = userRepository.findById(roleHistoryDTO.getUserId());
        User user = userOptional.get();

        user.setEndSellerTimestamp(TimeUtil.getCurrentTimestamp() + TIME_STAMP_IN_7_DAYS);
        user.setType(userTypeMapper.toEntity(userTypeService.findByName(SELLER_TYPE)));
        userRepository.save(user);
    }

    @Override
    public void declineUpgradeRequest(String requestId) {
        roleHistoryService.declineUpgradeRequest(requestId);
    }

    @Override
    public void downgradeUserRole(String userId) {
        final String BIDDER_TYPE = "Bidder";
        UserTypeMapper userTypeMapper = new UserTypeMapper();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null);

        if (user == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        if (user.getEndSellerTimestamp() == null)
            throw new GenericException(ErrorMessage.CAN_NOT_DOWNGRADE_USER_ROLE.getMessage());

        roleHistoryService.downgradeUserRole(userId);
        user.setEndSellerTimestamp(null);
        user.setType(userTypeMapper.toEntity(userTypeService.findByName(BIDDER_TYPE)));
        userRepository.save(user);
    }

    @Override
    public void changePoint(String userId, Boolean isLiked) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null);

        if (user == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());

        if (isLiked)
            user.setNumOfLike(user.getNumOfLike() + 1);
        else
            user.setNumOfDislike(user.getNumOfDislike() + 1);

        userRepository.save(user);
    }

    @Override
    public boolean isExistedAccount(String email) {
        return accountService.isExistedAccount(email);
    }

    @Override
    public String addNewUser(String email, String password, String name, String address) {
        String userId = UUID.randomUUID().toString();
        final String BIDDER_TYPE = "Bidder";
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userId);
        userDTO.setName(name);
        userDTO.setNumOfLike(0);
        userDTO.setNumOfDislike(0);
        userDTO.setAddress(address);
        userDTO.setUserType(userTypeService.findByName(BIDDER_TYPE));

        User user = userRepository.save(userMapper.toEntity(userDTO));
        accountService.addNewAccount(user, email, password);

        return userId;
    }

    @Override
    public void activateAccount(String userId) {
        accountService.activateAccount(userId);
    }
}
