package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.RoleHistoryDTO;
import com.hcmus.auction.model.entity.RoleHistory;
import com.hcmus.auction.model.mapper.RoleHistoryMapper;
import com.hcmus.auction.repository.RoleHistoryRepository;
import com.hcmus.auction.service.definition.RoleHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleHistoryServiceImpl implements RoleHistoryService {
    private RoleHistoryRepository roleHistoryRepository;
    private RoleHistoryMapper roleHistoryMapper;

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
}
