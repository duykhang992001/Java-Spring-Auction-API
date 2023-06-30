package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.FavoriteProductDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.entity.FavoriteProduct;
import com.hcmus.auction.model.mapper.FavoriteProductMapper;
import com.hcmus.auction.repository.FavoriteProductRepository;
import com.hcmus.auction.service.definition.FavoriteProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class FavoriteProductServiceImpl implements FavoriteProductService {
    private final FavoriteProductRepository favoriteProductRepository;
    private final FavoriteProductMapper favoriteProductMapper;
    private final ProductServiceImpl productService;

    @Override
    public Page<FavoriteProductDTO> getFavoriteProductsByUserId(String userId, Integer page, Integer size, Integer lte, Integer gte) {
        final String SORT_BY = "createdAt";
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Integer finalGte = gte == null ? currentTimestamp : gte;
        Pageable pageable = page != null && size != null ?
                PageRequest.of(page, size, Sort.by(SORT_BY).descending()) :
                PageRequest.of(0, Integer.MAX_VALUE, Sort.by(SORT_BY).descending());
        Page<FavoriteProduct> favoriteProductPage = lte == null ?
                favoriteProductRepository.findByUserIdAndProductEndTimestampGreaterThanEqual(userId, finalGte, pageable) :
                favoriteProductRepository.findByUserIdAndProductEndTimestampLessThanEqual(userId, lte, pageable);
        return favoriteProductPage.map(favoriteProductMapper::toDTO);
    }

    @Override
    public void addNewFavoriteProduct(String userId, String productId) {
        if (productService.getById(productId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_PRODUCT.getMessage());
        if (favoriteProductRepository.findByUserIdAndProductId(userId, productId).isPresent())
            throw new GenericException(ErrorMessage.EXITED_FAVORITE_PRODUCT.getMessage());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        String uuid = UUID.randomUUID().toString();
        FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO(uuid, userId, currentTimestamp, productDTO);

        favoriteProductRepository.save(favoriteProductMapper.toEntity(favoriteProductDTO));
    }

    @Override
    public void deleteFavoriteProduct(String userId, String productId) {
        if (productService.getById(productId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_PRODUCT.getMessage());
        if (favoriteProductRepository.findByUserIdAndProductId(userId, productId).isEmpty())
            throw new GenericException(ErrorMessage.NOT_EXISTED_FAVORITE_PRODUCT.getMessage());

        favoriteProductRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
