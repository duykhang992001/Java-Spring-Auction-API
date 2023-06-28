package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.DescriptionHistoryDTO;
import com.hcmus.auction.model.entity.DescriptionHistory;
import com.hcmus.auction.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class DescriptionHistoryMapper implements GenericMapper<DescriptionHistory, DescriptionHistoryDTO> {
    @Override
    public DescriptionHistory toEntity(DescriptionHistoryDTO descriptionHistoryDTO) {
        DescriptionHistory descriptionHistory = new DescriptionHistory();
        Product product = new Product();

        product.setId(descriptionHistoryDTO.getProductId());
        descriptionHistory.setId(descriptionHistoryDTO.getId());
        descriptionHistory.setContent(descriptionHistoryDTO.getContent());
        descriptionHistory.setCreatedAt(descriptionHistoryDTO.getCreatedAt());
        descriptionHistory.setProduct(product);

        return descriptionHistory;
    }

    @Override
    public DescriptionHistoryDTO toDTO(DescriptionHistory descriptionHistory) {
        return new DescriptionHistoryDTO(descriptionHistory.getId(), descriptionHistory.getProduct().getId(), descriptionHistory.getCreatedAt(), descriptionHistory.getContent());
    }
}
