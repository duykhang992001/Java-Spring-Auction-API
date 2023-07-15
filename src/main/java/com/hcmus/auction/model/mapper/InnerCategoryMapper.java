package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.InnerCategoryDTO;
import com.hcmus.auction.model.entity.InnerCategory;
import com.hcmus.auction.model.entity.OuterCategory;
import org.springframework.stereotype.Component;

@Component
public class InnerCategoryMapper implements GenericMapper<InnerCategory, InnerCategoryDTO> {
    @Override
    public InnerCategory toEntity(InnerCategoryDTO innerCategoryDTO) {
        InnerCategory innerCategory = new InnerCategory();
        OuterCategory outerCategory = new OuterCategory();

        outerCategory.setId(innerCategoryDTO.getOuterCategoryId());
        innerCategory.setId(innerCategoryDTO.getId());
        innerCategory.setName(innerCategoryDTO.getName());
        innerCategory.setOuterCategory(outerCategory);

        return innerCategory;
    }

    @Override
    public InnerCategoryDTO toDTO(InnerCategory innerCategory) {
        return new InnerCategoryDTO(innerCategory.getId(), innerCategory.getName(), innerCategory.getOuterCategory().getId());
    }
}
