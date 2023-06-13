package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.DescriptionHistoryDTO;
import com.hcmus.auction.model.entity.DescriptionHistory;
import org.springframework.stereotype.Component;

@Component
public class DescriptionHistoryMapper implements GenericMapper<DescriptionHistory, DescriptionHistoryDTO> {
    @Override
    public DescriptionHistory toEntity(DescriptionHistoryDTO descriptionHistoryDTO) {
        return null;
    }

    @Override
    public DescriptionHistoryDTO toDTO(DescriptionHistory descriptionHistory) {
        return new DescriptionHistoryDTO(descriptionHistory.getId(), descriptionHistory.getCreatedAt(), descriptionHistory.getContent());
    }
}
