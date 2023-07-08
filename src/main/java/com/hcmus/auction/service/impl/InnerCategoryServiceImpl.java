package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.InnerCategoryDTO;
import com.hcmus.auction.model.entity.InnerCategory;
import com.hcmus.auction.model.mapper.InnerCategoryMapper;
import com.hcmus.auction.repository.InnerCategoryRepository;
import com.hcmus.auction.service.definition.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InnerCategoryServiceImpl implements GenericService<InnerCategoryDTO, String> {
    private final InnerCategoryRepository innerCategoryRepository;
    private final InnerCategoryMapper innerCategoryMapper;

    @Override
    public InnerCategoryDTO getById(String id) {
        Optional<InnerCategory> innerCategoryOptional = innerCategoryRepository.findById(id);
        return innerCategoryOptional.map(innerCategoryMapper::toDTO).orElse(null);
    }
}
