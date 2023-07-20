package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.UserTypeDTO;
import com.hcmus.auction.model.entity.UserType;
import org.springframework.stereotype.Component;

@Component
public class UserTypeMapper implements GenericMapper<UserType, UserTypeDTO> {
    @Override
    public UserType toEntity(UserTypeDTO userTypeDTO) {
        UserType userType = new UserType();

        userType.setId(userTypeDTO.getId());
        userType.setName(userTypeDTO.getName());

        return userType;
    }

    @Override
    public UserTypeDTO toDTO(UserType userType) {
        return new UserTypeDTO(userType.getId(), userType.getName());
    }
}
