package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.entity.AuctionHistory;
import org.springframework.stereotype.Component;

@Component
public class AuctionHistoryMapper implements GenericMapper<AuctionHistory, AuctionHistoryDTO> {
    @Override
    public AuctionHistory toEntity(AuctionHistoryDTO auctionHistoryDTO) {
        return null;
    }

    @Override
    public AuctionHistoryDTO toDTO(AuctionHistory auctionHistory) {
        AuctionHistoryDTO auctionHistoryDTO = new AuctionHistoryDTO();
        UserMapper userMapper = new UserMapper();

        auctionHistoryDTO.setId(auctionHistory.getId());
        auctionHistoryDTO.setCreatedAt(auctionHistory.getCreatedAt());
        auctionHistoryDTO.setPrice(auctionHistory.getPrice());
        auctionHistoryDTO.setIsRejected(auctionHistory.getIsRejected());
        auctionHistoryDTO.setProductId(auctionHistory.getProduct().getId());
        auctionHistoryDTO.setBidder(userMapper.toDTO(auctionHistory.getUser()));

        return auctionHistoryDTO;
    }
}
