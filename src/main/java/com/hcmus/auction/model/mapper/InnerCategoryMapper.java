package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.InnerCategoryDTO;
import com.hcmus.auction.model.entity.InnerCategory;
import org.springframework.stereotype.Component;

@Component
public class InnerCategoryMapper implements GenericMapper<InnerCategory, InnerCategoryDTO> {
    @Override
    public InnerCategory toT(InnerCategoryDTO innerCategoryDTO) {
        return null;
    }

    @Override
    public InnerCategoryDTO toDTO(InnerCategory innerCategory) {
        return new InnerCategoryDTO(innerCategory.getId(), innerCategory.getName());
    }
}
