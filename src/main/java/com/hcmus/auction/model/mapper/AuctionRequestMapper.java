package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.AuctionRequestDTO;
import com.hcmus.auction.model.entity.AuctionRequest;
import com.hcmus.auction.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class AuctionRequestMapper implements GenericMapper<AuctionRequest, AuctionRequestDTO> {
    @Override
    public AuctionRequest toEntity(AuctionRequestDTO auctionRequestDTO) {
        AuctionRequest auctionRequest = new AuctionRequest();
        Product product = new Product();
        UserMapper userMapper = new UserMapper();

        product.setId(auctionRequestDTO.getProductId());
        auctionRequest.setId(auctionRequestDTO.getId());
        auctionRequest.setCreatedAt(auctionRequestDTO.getCreatedAt());
        auctionRequest.setIsAccepted(auctionRequestDTO.getIsAccepted());
        auctionRequest.setProduct(product);
        auctionRequest.setUser(userMapper.toEntity(auctionRequestDTO.getBidder()));

        return auctionRequest;
    }

    @Override
    public AuctionRequestDTO toDTO(AuctionRequest object) {
        return null;
    }
}
