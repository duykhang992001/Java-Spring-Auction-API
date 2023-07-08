package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.dto.ImageDTO;

import java.util.List;

public interface ImageService {
    void addListOfImages(List<ImageDTO> imageDTOs);
}
