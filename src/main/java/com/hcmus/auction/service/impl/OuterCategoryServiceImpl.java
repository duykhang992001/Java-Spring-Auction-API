package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.entity.OuterCategory;
import com.hcmus.auction.model.mapper.OuterCategoryMapper;
import com.hcmus.auction.repository.OuterCategoryRepository;
import com.hcmus.auction.service.definition.OuterCategoryService;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.UnPaginationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OuterCategoryServiceImpl implements UnPaginationService<OuterCategoryDTO>,
        GenericService<OuterCategoryDTO, String>,
        OuterCategoryService {
    private final OuterCategoryRepository outerCategoryRepository;
    private final OuterCategoryMapper outerCategoryMapper;
    private final ProductServiceImpl productService;

    @Override
    public List<OuterCategoryDTO> getAll() {
        List<OuterCategory> outerCategories = outerCategoryRepository.findAll();
        return outerCategories.stream()
                .map(outerCategoryMapper::toDTO)
                .toList();
    }

    @Override
    public OuterCategoryDTO getById(String id) {
        Optional<OuterCategory> outerCategoryOptional = outerCategoryRepository.findById(id);
        return outerCategoryOptional.map(outerCategoryMapper::toDTO).orElse(null);
    }

    @Override
    public Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size, Integer lte, Integer gte) {
        return productService.getProductsByCategoryId(categoryId, exclusiveProductId, page, size, lte, gte);
    }
}
