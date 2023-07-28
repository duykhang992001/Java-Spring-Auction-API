package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.model.dto.AuctionRequestDTO;
import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.AuctionRequest;
import com.hcmus.auction.model.mapper.AuctionRequestMapper;
import com.hcmus.auction.repository.AuctionRequestRepository;
import com.hcmus.auction.service.definition.AuctionRequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuctionRequestServiceImpl implements AuctionRequestService {
    private final AuctionRequestRepository auctionRequestRepository;
    private final AuctionRequestMapper auctionRequestMapper;

    @Override
    public int getRequestStatus(String userId, String productId) {
        final String SORT_BY = "createdAt";
        final int CAN_NOT_SEND_REQUEST = 0;
        final int CAN_SEND_REQUEST = 1;
        final int ABLE_TO_AUCTION = 2;
        Pageable pageable = PageRequest.of(0, 1, Sort.by(SORT_BY).descending());
        Page<AuctionRequest> auctionRequestPage = auctionRequestRepository.findAllByUserIdAndProductId(userId, productId, pageable);
        List<AuctionRequest> auctionRequests = auctionRequestPage.getContent();
        if (auctionRequests.isEmpty() || (auctionRequests.get(0).getIsAccepted() != null && !auctionRequests.get(0).getIsAccepted()))
            return CAN_SEND_REQUEST;
        else if (auctionRequests.get(0).getIsAccepted() != null && auctionRequests.get(0).getIsAccepted())
            return ABLE_TO_AUCTION;
        return CAN_NOT_SEND_REQUEST;
    }

    @Override
    public void addNewAuctionRequest(String userId, String productId) {
        AuctionRequestDTO auctionRequestDTO = new AuctionRequestDTO();
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userId);
        auctionRequestDTO.setId(UUID.randomUUID().toString());
        auctionRequestDTO.setCreatedAt(TimeUtil.getCurrentTimestamp());
        auctionRequestDTO.setProductId(productId);
        auctionRequestDTO.setIsAccepted(null);
        auctionRequestDTO.setBidder(userDTO);

        auctionRequestRepository.save(auctionRequestMapper.toEntity(auctionRequestDTO));
    }
}
