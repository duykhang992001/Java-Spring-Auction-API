package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.InnerCategoryDTO;
import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.model.entity.OuterCategory;
import com.hcmus.auction.model.mapper.OuterCategoryMapper;
import com.hcmus.auction.repository.OuterCategoryRepository;
import com.hcmus.auction.service.definition.OuterCategoryService;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.UnPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OuterCategoryServiceImpl implements UnPaginationService<OuterCategoryDTO>,
        GenericService<OuterCategoryDTO, String>,
        OuterCategoryService {
    @Autowired
    private OuterCategoryRepository outerCategoryRepository;

    @Autowired
    private OuterCategoryMapper outerCategoryMapper;

    @Autowired
    private InnerCategoryServiceImpl innerCategoryService;

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
    public void addNewOuterCategory(String categoryName) {
        OuterCategoryDTO outerCategoryDTO = new OuterCategoryDTO();

        outerCategoryDTO.setId(UUID.randomUUID().toString());
        outerCategoryDTO.setName(categoryName);

        outerCategoryRepository.save(outerCategoryMapper.toEntity(outerCategoryDTO));
    }

    @Override
    public void updateOuterCategoryById(String outerCategoryId, String newCategoryName) {
        Optional<OuterCategory> outerCategoryOptional = outerCategoryRepository.findById(outerCategoryId);
        OuterCategory outerCategory = outerCategoryOptional.orElse(null);

        if (outerCategory != null) {
            outerCategory.setName(newCategoryName);
            outerCategoryRepository.save(outerCategory);
        } else
            throw new GenericException(ErrorMessage.NOT_EXISTED_OUTER_CATEGORY.getMessage());
    }

    @Override
    public void deleteOuterCategoryById(String outerCategoryId) {
        OuterCategoryDTO outerCategoryDTO = this.getById(outerCategoryId);
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();

        if (outerCategoryDTO != null) {
            List<InnerCategoryDTO> innerCategoryDTOs = outerCategoryDTO.getInnerCategoryDTOs();
            if (innerCategoryDTOs.stream().allMatch(innerCategoryDTO ->
                    innerCategoryService.getProductsByCategoryId(innerCategoryDTO.getId(), null, 0, 1, null, currentTimestamp).isEmpty())) {
                outerCategoryRepository.deleteById(outerCategoryId);
            } else
                throw new GenericException(ErrorMessage.CAN_NOT_DELETE_CATEGORY_CONTAINING_PRODUCT.getMessage());
        } else
            throw new GenericException(ErrorMessage.NOT_EXISTED_OUTER_CATEGORY.getMessage());
    }
}
