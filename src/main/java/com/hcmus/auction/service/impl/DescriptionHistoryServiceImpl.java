package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.model.dto.DescriptionHistoryDTO;
import com.hcmus.auction.model.mapper.DescriptionHistoryMapper;
import com.hcmus.auction.repository.DescriptionHistoryRepository;
import com.hcmus.auction.service.definition.DescriptionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DescriptionHistoryServiceImpl implements DescriptionHistoryService {
    private final DescriptionHistoryRepository descriptionHistoryRepository;
    private final DescriptionHistoryMapper descriptionHistoryMapper;

    @Override
    public void addNewProductDescription(String productId, String content) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        String uuid = UUID.randomUUID().toString();
        DescriptionHistoryDTO descriptionHistoryDTO = new DescriptionHistoryDTO(uuid, productId, currentTimestamp, content);
        descriptionHistoryRepository.save(descriptionHistoryMapper.toEntity(descriptionHistoryDTO));
    }
}
