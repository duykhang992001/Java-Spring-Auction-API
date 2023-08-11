package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.User;
import com.hcmus.auction.model.entity.UserType;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements GenericMapper<User, UserDTO> {
    @Override
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        UserType userType = new UserType();

        userType.setId(userDTO.getUserType().getId());
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setNumOfDislike(userDTO.getNumOfDislike());
        user.setNumOfLike(userDTO.getNumOfLike());
        user.setAddress(userDTO.getAddress());
        user.setType(userType);

        return user;
    }

    @Override
    public UserDTO toDTO(User user) {
        UserTypeMapper userTypeMapper = new UserTypeMapper();
        return user != null ?
                new UserDTO(user.getId(), user.getName(), user.getAddress(), user.getNumOfLike(),
                        user.getNumOfDislike(), userTypeMapper.toDTO(user.getType())) : null;
    }
}
