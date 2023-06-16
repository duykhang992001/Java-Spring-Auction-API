package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.entity.OuterCategory;
import com.hcmus.auction.model.mapper.OuterCategoryMapper;
import com.hcmus.auction.repository.OuterCategoryRepository;
import com.hcmus.auction.service.definition.CategoryService;
import com.hcmus.auction.service.definition.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements GenericService<OuterCategoryDTO, String>, CategoryService {
    private final OuterCategoryRepository outerCategoryRepository;
    private final OuterCategoryMapper outerCategoryMapper;
    private final ProductServiceImpl productService;

    @Override
    public Page<OuterCategoryDTO> getAll(Integer page, Integer size) {
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<OuterCategory> outerCategoryPage = outerCategoryRepository.findAll(pageable);
        return outerCategoryPage.map(outerCategoryMapper::toDTO);
    }

    @Override
    public OuterCategoryDTO getById(String id) {
        Optional<OuterCategory> outerCategoryOptional = outerCategoryRepository.findById(id);
        return outerCategoryOptional.map(outerCategoryMapper::toDTO).orElse(null);
    }

    @Override
    public Page<ProductDTO> getProductsByCategoryId(String categoryId, Integer page, Integer size) {
        return productService.getProductsByCategoryId(categoryId, page, size);
    }
}
