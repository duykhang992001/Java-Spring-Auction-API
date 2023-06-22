package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.entity.AuctionHistory;
import com.hcmus.auction.model.mapper.AuctionHistoryMapper;
import com.hcmus.auction.repository.AuctionHistoryRepository;
import com.hcmus.auction.service.definition.AuctionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuctionHistoryServiceImpl implements AuctionHistoryService {
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionHistoryMapper auctionHistoryMapper;

    @Override
    public Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy) {
        final String SORT_BY = "createdAt";
        Sort sort = orderBy == null || orderBy.equals("desc") ? Sort.by(SORT_BY).descending() : Sort.by(SORT_BY).ascending();
        Pageable pageable = page != null && size != null ?
                PageRequest.of(page, size, sort) :
                PageRequest.of(0, Integer.MAX_VALUE, sort);
        Page<AuctionHistory> auctionHistoryPage = auctionHistoryRepository.findAllByProductId(productId, pageable);
        return auctionHistoryPage.map(auctionHistoryMapper::toDTO);
    }
}
