package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.RoleHistoryDTO;
import com.hcmus.auction.model.entity.RoleHistory;
import com.hcmus.auction.model.mapper.RoleHistoryMapper;
import com.hcmus.auction.repository.RoleHistoryRepository;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.RoleHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleHistoryServiceImpl implements GenericService<RoleHistoryDTO, String>, RoleHistoryService {
    private RoleHistoryRepository roleHistoryRepository;
    private RoleHistoryMapper roleHistoryMapper;

    @Override
    public RoleHistoryDTO getById(String id) {
        Optional<RoleHistory> roleHistoryOptional = roleHistoryRepository.findById(id);
        return roleHistoryOptional.map(roleHistoryMapper::toDTO).orElse(null);
    }

    @Override
    public Page<RoleHistoryDTO> getUnacceptedUpgradeRequests(Integer page, Integer size) {
        final String SORT_BY = "createdAt";
        final Boolean isUpgraded = true;
        Pageable pageable = page != null && size != null ?
                PageRequest.of(page, size, Sort.by(SORT_BY).descending()) :
                PageRequest.of(0, Integer.MAX_VALUE, Sort.by(SORT_BY).descending());
        Page<RoleHistory> roleHistoryPage = roleHistoryRepository.findAllByIsAcceptedAndIsUpgraded(null, isUpgraded, pageable);
        return roleHistoryPage.map(roleHistoryMapper::toDTO);
    }

    @Override
    public void addNewRoleHistory(String userId) {
        final String SORT_BY = "createdAt";
        final int PAGE = 0, SIZE = 1, TIMESTAMP_DIFF_IN_7_DAYS = 604800;
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.by(SORT_BY).descending());
        Page<RoleHistory> roleHistoryPage = roleHistoryRepository.findByUserId(userId, pageable);
        RoleHistory latestRoleHistory = roleHistoryPage.getContent().isEmpty() ? null : roleHistoryPage.getContent().get(0);

        if (latestRoleHistory == null || !latestRoleHistory.getIsUpgraded() ||
                (latestRoleHistory.getIsUpgraded() &&
                latestRoleHistory.getIsAccepted() != null &&
                (!latestRoleHistory.getIsAccepted() || currentTimestamp - latestRoleHistory.getUpdatedAt() > TIMESTAMP_DIFF_IN_7_DAYS))) {
            String uuid = UUID.randomUUID().toString();
            RoleHistoryDTO roleHistoryDTO = new RoleHistoryDTO();

            roleHistoryDTO.setId(uuid);
            roleHistoryDTO.setCreatedAt(currentTimestamp);
            roleHistoryDTO.setUserId(userId);
            roleHistoryDTO.setIsAccepted(null);
            roleHistoryDTO.setIsUpgraded(true);
            roleHistoryDTO.setUpdatedAt(null);

            roleHistoryRepository.save(roleHistoryMapper.toEntity(roleHistoryDTO));
        } else {
            throw new GenericException(ErrorMessage.CAN_NOT_UPGRADE_ROLE.getMessage());
        }
    }

    @Override
    public void acceptUpgradeRequest(String requestId) {
        Optional<RoleHistory> roleHistoryOptional = roleHistoryRepository.findById(requestId);
        RoleHistory roleHistory = roleHistoryOptional.orElse(null);

        if (roleHistory == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_UPGRADE_REQUEST.getMessage());

        if (!roleHistory.getIsUpgraded() || roleHistory.getIsAccepted() != null)
            throw new GenericException(ErrorMessage.CAN_NOT_ACCEPT_UPGRADE_REQUEST.getMessage());

        roleHistory.setIsAccepted(true);
        roleHistory.setUpdatedAt(TimeUtil.getCurrentTimestamp());
        roleHistoryRepository.save(roleHistory);
    }

    @Override
    public void declineUpgradeRequest(String requestId) {
        Optional<RoleHistory> roleHistoryOptional = roleHistoryRepository.findById(requestId);
        RoleHistory roleHistory = roleHistoryOptional.orElse(null);

        if (roleHistory == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_UPGRADE_REQUEST.getMessage());

        if (!roleHistory.getIsUpgraded() || roleHistory.getIsAccepted() != null)
            throw new GenericException(ErrorMessage.CAN_NOT_DECLINE_UPGRADE_REQUEST.getMessage());

        roleHistory.setIsAccepted(false);
        roleHistory.setUpdatedAt(TimeUtil.getCurrentTimestamp());
        roleHistoryRepository.save(roleHistory);
    }

    @Override
    public void downgradeUserRole(String userId) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        RoleHistoryDTO roleHistoryDTO = new RoleHistoryDTO();

        roleHistoryDTO.setId(UUID.randomUUID().toString());
        roleHistoryDTO.setUserId(userId);
        roleHistoryDTO.setCreatedAt(currentTimestamp);
        roleHistoryDTO.setUpdatedAt(currentTimestamp);
        roleHistoryDTO.setIsUpgraded(false);
        roleHistoryDTO.setIsAccepted(true);

        roleHistoryRepository.save(roleHistoryMapper.toEntity(roleHistoryDTO));
    }
}
