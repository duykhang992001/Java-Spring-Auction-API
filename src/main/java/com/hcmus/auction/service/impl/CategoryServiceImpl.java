package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.model.entity.OuterCategory;
import com.hcmus.auction.model.mapper.OuterCategoryMapper;
import com.hcmus.auction.repository.OuterCategoryRepository;
import com.hcmus.auction.service.definition.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements GenericService<OuterCategoryDTO, String> {
    private final OuterCategoryRepository outerCategoryRepository;
    private final OuterCategoryMapper outerCategoryMapper;

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
}
