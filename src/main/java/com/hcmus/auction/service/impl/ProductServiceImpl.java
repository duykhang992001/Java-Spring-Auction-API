package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.RequestParamUtil;
import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.mapper.ProductMapper;
import com.hcmus.auction.repository.ProductRepository;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.PaginationService;
import com.hcmus.auction.service.definition.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements PaginationService<ProductDTO>,
        GenericService<ProductDTO, String>,
        ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuctionHistoryServiceImpl auctionHistoryService;
    private final DescriptionHistoryServiceImpl descriptionHistoryService;

    @Override
    public Page<ProductDTO> getAll(Integer page, Integer size, String sortBy, String orderBy, String keyword, Integer lte, Integer gte) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Integer finalGte = gte == null ? currentTimestamp : gte;
        String sortParam = keyword != null ? RequestParamUtil.formatProductSortByParameter(sortBy) : sortBy;
        Sort sort = sortBy != null && orderBy != null ?
                (orderBy.equals("asc") ? Sort.by(sortParam).ascending() : Sort.by(sortParam).descending()) :
                Sort.unsorted();
        Pageable pageable = page != null && size != null ?
                PageRequest.of(page, size, sort) :
                PageRequest.of(0, Integer.MAX_VALUE, sort);
        Page<Product> productPage = keyword != null ?
                (lte == null ? productRepository.searchWithGteEndTimestamp(keyword, finalGte, pageable) :
                        productRepository.searchWithLteEndTimestamp(keyword, lte, pageable)) :
                (lte == null ? productRepository.findAllByEndTimestampGreaterThanEqual(finalGte, pageable) :
                        productRepository.findAllByEndTimestampLessThanEqual(lte, pageable));
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public ProductDTO getById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(productMapper::toDTO).orElse(null);
    }

    @Override
    public Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size, Integer lte, Integer gte) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Integer finalGte = gte == null ? currentTimestamp : gte;
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<Product> productPage = exclusiveProductId == null ?
                (lte == null ?
                        productRepository.findAllByCategoryIdAndEndTimestampGreaterThanEqual(categoryId, finalGte, pageable) :
                        productRepository.findAllByCategoryIdAndEndTimestampLessThanEqual(categoryId, lte, pageable)) :
                (lte == null ?
                        productRepository.findAllByCategoryIdAndEndTimestampGreaterThanEqualAndIdNot(categoryId, finalGte, exclusiveProductId, pageable) :
                        productRepository.findAllByCategoryIdAndEndTimestampLessThanEqualAndIdNot(categoryId, lte, exclusiveProductId, pageable));
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy) {
        return auctionHistoryService.getAuctionHistoriesByProductId(productId, page, size, orderBy);
    }

    @Override
    public Page<ProductDTO> getAuctioningProductsByUserId(String userId, Integer page, Integer size) {
        Page<String> productIdPage = auctionHistoryService.getAuctioningProductIdByUserId(userId, page, size);
        return productIdPage.map(this::getById);
    }

    @Override
    public void addNewProductDescription(String productId, String content) {
        if (this.getById(productId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_PRODUCT.getMessage());
        descriptionHistoryService.addNewProductDescription(productId, content);
    }
}
