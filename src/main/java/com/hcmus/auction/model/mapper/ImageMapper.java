package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.ImageDTO;
import com.hcmus.auction.model.entity.Image;
import com.hcmus.auction.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper implements GenericMapper<Image, ImageDTO> {
    @Override
    public Image toEntity(ImageDTO imageDTO) {
        Image image = new Image();
        Product product = new Product();

        product.setId(imageDTO.getProductId());
        image.setId(imageDTO.getId());
        image.setUrl(imageDTO.getUrl());
        image.setIsThumbnailImage(imageDTO.getIsThumbnailImage());
        image.setProduct(product);

        return image;
    }

    @Override
    public ImageDTO toDTO(Image image) {
        return new ImageDTO(image.getId(), image.getUrl(), image.getIsThumbnailImage(), image.getProduct().getId());
    }
}
