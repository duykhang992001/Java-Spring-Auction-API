package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.mapper.ProductMapper;
import com.hcmus.auction.repository.ProductRepository;
import com.hcmus.auction.service.definition.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements GenericService<ProductDTO, String> {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDTO> getAll(Integer page, Integer size) {
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public ProductDTO getById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(productMapper::toDTO).orElse(null);
    }
}
