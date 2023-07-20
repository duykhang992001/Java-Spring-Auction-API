package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.UserTypeDTO;

public interface UserTypeService {
    UserTypeDTO findByName(String name);
}
