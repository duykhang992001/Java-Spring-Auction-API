package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements GenericMapper<User, UserDTO> {
    @Override
    public User toEntity(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO toDTO(User user) {
        return user != null ?
                new UserDTO(user.getId(), user.getName(), user.getAddress(), user.getNumOfLike(), user.getNumOfDislike()) : null;
    }
}
