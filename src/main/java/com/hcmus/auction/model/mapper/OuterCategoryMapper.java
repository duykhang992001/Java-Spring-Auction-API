package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.model.entity.OuterCategory;
import org.springframework.stereotype.Component;

@Component
public class OuterCategoryMapper implements GenericMapper<OuterCategory, OuterCategoryDTO> {
    @Override
    public OuterCategory toEntity(OuterCategoryDTO outerCategoryDTO) {
        OuterCategory outerCategory = new OuterCategory();

        outerCategory.setId(outerCategoryDTO.getId());
        outerCategory.setName(outerCategoryDTO.getName());

        return outerCategory;
    }

    @Override
    public OuterCategoryDTO toDTO(OuterCategory outerCategory) {
        OuterCategoryDTO outerCategoryDTO = new OuterCategoryDTO();
        InnerCategoryMapper innerCategoryMapper = new InnerCategoryMapper();

        outerCategoryDTO.setId(outerCategory.getId());
        outerCategoryDTO.setName(outerCategory.getName());
        outerCategoryDTO.setInnerCategoryDTOs(
            outerCategory.getInnerCategories()
                    .stream()
                    .map(innerCategoryMapper::toDTO)
                    .toList()
        );

        return outerCategoryDTO;
    }
}
