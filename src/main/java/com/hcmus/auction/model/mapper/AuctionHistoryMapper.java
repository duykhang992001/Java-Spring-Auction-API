package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.entity.AuctionHistory;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuctionHistoryMapper implements GenericMapper<AuctionHistory, AuctionHistoryDTO> {
    @Override
    public AuctionHistory toEntity(AuctionHistoryDTO auctionHistoryDTO) {
        AuctionHistory auctionHistory = new AuctionHistory();
        Product product = new Product();
        User user = new User();

        product.setId(auctionHistoryDTO.getProductId());
        user.setId(auctionHistoryDTO.getBidder().getId());
        auctionHistory.setId(auctionHistoryDTO.getId());
        auctionHistory.setPrice(auctionHistoryDTO.getPrice());
        auctionHistory.setCreatedAt(auctionHistoryDTO.getCreatedAt());
        auctionHistory.setIsRejected(auctionHistoryDTO.getIsRejected());
        auctionHistory.setProduct(product);
        auctionHistory.setUser(user);

        return auctionHistory;
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
