package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.RoleHistoryDTO;
import org.springframework.data.domain.Page;

public interface RoleHistoryService {
    Page<RoleHistoryDTO> getUnacceptedUpgradeRequests(Integer page, Integer size);
    void addNewRoleHistory(String userId);
    void acceptUpgradeRequest(String requestId);
    void declineUpgradeRequest(String requestId);
}
