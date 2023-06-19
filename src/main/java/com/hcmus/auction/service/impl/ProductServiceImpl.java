package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
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
public class ProductServiceImpl implements PaginationService<ProductDTO>, GenericService<ProductDTO, String>, ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDTO> getAll(Integer page, Integer size, String sortBy, String orderBy) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Sort sort = sortBy != null && orderBy != null ?
                (orderBy.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()) :
                Sort.unsorted();
        Pageable pageable = page != null && size != null ?
                PageRequest.of(page, size, sort) :
                PageRequest.of(0, Integer.MAX_VALUE, sort);
        Page<Product> productPage = productRepository.findAllByEndTimestampGreaterThanEqual(currentTimestamp, pageable);
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public ProductDTO getById(String id) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Optional<Product> productOptional = productRepository.findByIdAndEndTimestampGreaterThanEqual(id, currentTimestamp);
        return productOptional.map(productMapper::toDTO).orElse(null);
    }

    @Override
    public Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<Product> productPage = exclusiveProductId == null ?
                productRepository.findAllByCategoryIdAndEndTimestampGreaterThanEqual(categoryId, currentTimestamp, pageable) :
                productRepository.findAllByCategoryIdAndEndTimestampGreaterThanEqualAndIdNot(categoryId, currentTimestamp, exclusiveProductId, pageable);
        return productPage.map(productMapper::toDTO);
    }
}
