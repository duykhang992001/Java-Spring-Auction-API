package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
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
import java.util.Optional;
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

    @Override
    public Page<AuctionRequestDTO> getUnacceptedAuctionRequestsByProductId(String productId, Integer page, Integer size) {
        final String SORT_BY = "createdAt";
        Pageable pageable = page != null && size != null ?
                PageRequest.of(page, size, Sort.by(SORT_BY).descending()) :
                PageRequest.of(0, Integer.MAX_VALUE, Sort.by(SORT_BY).descending());
        Page<AuctionRequest> auctionRequestPage = auctionRequestRepository.findAllByProductIdAndIsAccepted(productId, null, pageable);
        return auctionRequestPage.map(auctionRequestMapper::toDTO);
    }

    @Override
    public void acceptAuctionRequest(String requestId) {
        Optional<AuctionRequest> auctionRequestOptional = auctionRequestRepository.findById(requestId);
        AuctionRequest auctionRequest = auctionRequestOptional.orElse(null);

        if (auctionRequest == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_AUCTION_REQUEST.getMessage());
        if (auctionRequest.getIsAccepted() != null)
            throw new GenericException(ErrorMessage.CAN_NOT_ACCEPT_AUCTION_REQUEST.getMessage());

        auctionRequest.setIsAccepted(true);
        auctionRequestRepository.save(auctionRequest);
    }

    @Override
    public void declineAuctionRequest(String requestId) {
        Optional<AuctionRequest> auctionRequestOptional = auctionRequestRepository.findById(requestId);
        AuctionRequest auctionRequest = auctionRequestOptional.orElse(null);

        if (auctionRequest == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_AUCTION_REQUEST.getMessage());
        if (auctionRequest.getIsAccepted() != null)
            throw new GenericException(ErrorMessage.CAN_NOT_DECLINE_AUCTION_REQUEST.getMessage());

        auctionRequest.setIsAccepted(false);
        auctionRequestRepository.save(auctionRequest);
    }
}
