package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.ImageDTO;
import com.hcmus.auction.model.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper implements GenericMapper<Image, ImageDTO> {
    @Override
    public Image toEntity(ImageDTO imageDTO) {
        return null;
    }

    @Override
    public ImageDTO toDTO(Image image) {
        return new ImageDTO(image.getId(), image.getUrl(), image.getIsThumbnailImage());
    }
}
