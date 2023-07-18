package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.RoleHistoryDTO;
import com.hcmus.auction.model.entity.RoleHistory;
import com.hcmus.auction.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class RoleHistoryMapper implements GenericMapper<RoleHistory, RoleHistoryDTO> {
    @Override
    public RoleHistory toEntity(RoleHistoryDTO roleHistoryDTO) {
        RoleHistory roleHistory = new RoleHistory();
        User user = new User();

        user.setId(roleHistoryDTO.getUserId());
        roleHistory.setId(roleHistoryDTO.getId());
        roleHistory.setCreatedAt(roleHistoryDTO.getCreatedAt());
        roleHistory.setUpdatedAt(roleHistoryDTO.getUpdatedAt());
        roleHistory.setIsAccepted(roleHistoryDTO.getIsAccepted());
        roleHistory.setIsUpgraded(roleHistoryDTO.getIsUpgraded());
        roleHistory.setUser(user);

        return roleHistory;
    }

    @Override
    public RoleHistoryDTO toDTO(RoleHistory roleHistory) {
        return new RoleHistoryDTO(roleHistory.getId(), roleHistory.getUser().getId(), roleHistory.getCreatedAt(), roleHistory.getUpdatedAt(), roleHistory.getIsAccepted(), roleHistory.getIsUpgraded());
    }
}
