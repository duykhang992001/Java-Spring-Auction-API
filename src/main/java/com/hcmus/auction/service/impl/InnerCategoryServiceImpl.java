package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.InnerCategoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.entity.InnerCategory;
import com.hcmus.auction.model.mapper.InnerCategoryMapper;
import com.hcmus.auction.repository.InnerCategoryRepository;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.InnerCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class InnerCategoryServiceImpl implements GenericService<InnerCategoryDTO, String>, InnerCategoryService {
    @Autowired
    private InnerCategoryRepository innerCategoryRepository;

    @Autowired
    private InnerCategoryMapper innerCategoryMapper;

    @Autowired
    private OuterCategoryServiceImpl outerCategoryService;

    @Autowired
    private ProductServiceImpl productService;

    @Override
    public InnerCategoryDTO getById(String id) {
        Optional<InnerCategory> innerCategoryOptional = innerCategoryRepository.findById(id);
        return innerCategoryOptional.map(innerCategoryMapper::toDTO).orElse(null);
    }

    @Override
    public Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size, Integer lte, Integer gte) {
        return productService.getProductsByCategoryId(categoryId, exclusiveProductId, page, size, lte, gte);
    }

    @Override
    public void addNewInnerCategory(String outerCategoryId, String categoryName) {
        if (outerCategoryService.getById(outerCategoryId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_OUTER_CATEGORY.getMessage());

        InnerCategoryDTO innerCategoryDTO = new InnerCategoryDTO();

        innerCategoryDTO.setId(UUID.randomUUID().toString());
        innerCategoryDTO.setName(categoryName);
        innerCategoryDTO.setOuterCategoryId(outerCategoryId);

        innerCategoryRepository.save(innerCategoryMapper.toEntity(innerCategoryDTO));
    }

    @Override
    public void updateInnerCategoryById(String innerCategoryId, String newCategoryName) {
        Optional<InnerCategory> innerCategoryOptional = innerCategoryRepository.findById(innerCategoryId);
        InnerCategory innerCategory = innerCategoryOptional.orElse(null);

        if (innerCategory != null) {
            innerCategory.setName(newCategoryName);
            innerCategoryRepository.save(innerCategory);
        } else
            throw new GenericException(ErrorMessage.NOT_EXISTED_INNER_CATEGORY.getMessage());
    }

    @Override
    public void deleteInnerCategoryById(String innerCategoryId) {
        if (this.getById(innerCategoryId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_INNER_CATEGORY.getMessage());
        if (!this.getProductsByCategoryId(
                innerCategoryId, null, 0, 1, null, TimeUtil.getCurrentTimestamp()).isEmpty())
            throw new GenericException(ErrorMessage.CAN_NOT_DELETE_CATEGORY_CONTAINING_PRODUCT.getMessage());

        innerCategoryRepository.deleteById(innerCategoryId);
    }
}
