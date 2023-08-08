package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.UserDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuctionHistoryServiceImpl implements AuctionHistoryService {
    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionHistoryMapper auctionHistoryMapper;

    @Override
    public Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy) {
        final String SORT_BY = "createdAt";
        final Boolean isRejected = false;
        Sort sort = orderBy == null || orderBy.equals("desc") ? Sort.by(SORT_BY).descending() : Sort.by(SORT_BY).ascending();
        Pageable pageable = page != null && size != null ?
                PageRequest.of(page, size, sort) :
                PageRequest.of(0, Integer.MAX_VALUE, sort);
        Page<AuctionHistory> auctionHistoryPage = auctionHistoryRepository.findAllByProductIdAndIsRejected(productId, isRejected, pageable);
        return auctionHistoryPage.map(auctionHistoryMapper::toDTO);
    }

    @Override
    public Page<String> getAuctioningProductIdByUserId(String userId, Integer page, Integer size) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        return auctionHistoryRepository.getAuctioningProductIdByUserId(userId, currentTimestamp, pageable);
    }

    @Override
    public void addNewAuctionHistory(String userId, String productId, Integer price) {
        AuctionHistoryDTO auctionHistoryDTO = new AuctionHistoryDTO();
        UserDTO bidder = new UserDTO();

        bidder.setId(userId);
        auctionHistoryDTO.setId(UUID.randomUUID().toString());
        auctionHistoryDTO.setPrice(price);
        auctionHistoryDTO.setProductId(productId);
        auctionHistoryDTO.setCreatedAt(TimeUtil.getCurrentTimestamp());
        auctionHistoryDTO.setIsRejected(false);
        auctionHistoryDTO.setBidder(bidder);

        auctionHistoryRepository.save(auctionHistoryMapper.toEntity(auctionHistoryDTO));
    }

    @Override
    public List<String> declineAuctionHistory(String auctionId) {
        Optional<AuctionHistory> auctionHistoryOptional = auctionHistoryRepository.findById(auctionId);
        AuctionHistory auctionHistory = auctionHistoryOptional.orElse(null);

        if (auctionHistory == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_AUCTION_HISTORY.getMessage());
        if (auctionHistory.getIsRejected())
            throw new GenericException(ErrorMessage.CAN_NOT_DECLINE_AUCTION_HISTORY.getMessage());

        String userId = auctionHistory.getUser().getId();
        String productId = auctionHistory.getProduct().getId();
        List<AuctionHistory> auctionHistoriesBeDeclined = auctionHistoryRepository.findAllByUserIdAndProductId(userId, productId);
        for (AuctionHistory auctionHistoryBeDeclined : auctionHistoriesBeDeclined) {
            auctionHistoryBeDeclined.setIsRejected(true);
        }
        auctionHistoryRepository.saveAll(auctionHistoriesBeDeclined);

        return List.of(userId, productId, String.valueOf(auctionHistoriesBeDeclined.size()));
    }

    @Override
    public boolean isAbleToAuction(String userId, String productId) {
        List<AuctionHistory> auctionHistories = auctionHistoryRepository.findAllByUserIdAndProductId(userId, productId);
        return !auctionHistories.get(0).getIsRejected();
    }
}
