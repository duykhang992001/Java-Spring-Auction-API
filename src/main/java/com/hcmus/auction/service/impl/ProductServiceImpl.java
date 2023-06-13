package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.mapper.ProductMapper;
import com.hcmus.auction.repository.ProductRepository;
import com.hcmus.auction.service.definition.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements GenericService<ProductDTO, String> {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDTO getById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(productMapper::toDTO).orElse(null);
    }
}
