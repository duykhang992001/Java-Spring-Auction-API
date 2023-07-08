package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.variable.request.ImageRequest;
import com.hcmus.auction.model.dto.ImageDTO;
import com.hcmus.auction.model.entity.Image;
import com.hcmus.auction.model.mapper.ImageMapper;
import com.hcmus.auction.repository.ImageRepository;
import com.hcmus.auction.service.definition.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    public void addListOfImages(List<ImageDTO> imageDTOs) {
        List<Image> images = imageDTOs.stream().map(imageMapper::toEntity).toList();
        imageRepository.saveAll(images);
    }
}
