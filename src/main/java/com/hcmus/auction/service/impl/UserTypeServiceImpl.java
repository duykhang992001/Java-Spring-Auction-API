package com.hcmus.auction.service.impl;

import com.hcmus.auction.model.dto.UserTypeDTO;
import com.hcmus.auction.model.entity.UserType;
import com.hcmus.auction.model.mapper.UserTypeMapper;
import com.hcmus.auction.repository.UserTypeRepository;
import com.hcmus.auction.service.definition.UserTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    @Override
    public UserTypeDTO findByName(String name) {
        Optional<UserType> userTypeOptional = userTypeRepository.findByName(name);
        return userTypeOptional.map(userTypeMapper::toDTO).orElse(null);
    }
}
