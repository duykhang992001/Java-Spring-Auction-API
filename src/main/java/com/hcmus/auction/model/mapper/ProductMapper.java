package com.hcmus.auction.model.mapper;

import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.entity.InnerCategory;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements GenericMapper<Product, ProductDTO> {
    @Override
    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        User user = new User();
        InnerCategory category = new InnerCategory();

        user.setId(productDTO.getOwner().getId());
        category.setId(productDTO.getCategory().getId());
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCurrentPrice(productDTO.getCurrentPrice());
        product.setBuyNowPrice(productDTO.getBuyNowPrice());
        product.setNumOfBid(productDTO.getNumOfBid());
        product.setAdditionalPrice(productDTO.getAdditionalPrice());
        product.setStartTimestamp(productDTO.getStartTimestamp());
        product.setEndTimestamp(productDTO.getEndTimestamp());
        product.setIsAutoExtendTime(productDTO.getIsAutoExtendTime());
        product.setOwner(user);
        product.setCategory(category);

        return product;
    }

    @Override
    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        InnerCategoryMapper innerCategoryMapper = new InnerCategoryMapper();
        UserMapper userMapper = new UserMapper();
        ImageMapper imageMapper = new ImageMapper();
        DescriptionHistoryMapper descriptionHistoryMapper = new DescriptionHistoryMapper();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCurrentPrice(product.getCurrentPrice());
        productDTO.setBuyNowPrice(product.getBuyNowPrice());
        productDTO.setStartTimestamp(product.getStartTimestamp());
        productDTO.setEndTimestamp(product.getEndTimestamp());
        productDTO.setNumOfBid(product.getNumOfBid());
        productDTO.setAdditionalPrice(product.getAdditionalPrice());
        productDTO.setIsAutoExtendTime(product.getIsAutoExtendTime());
        productDTO.setCategory(innerCategoryMapper.toDTO(product.getCategory()));
        productDTO.setCurrentWinner(userMapper.toDTO(product.getCurrentWinner()));
        productDTO.setOwner(userMapper.toDTO(product.getOwner()));
        productDTO.setImages(
            product.getImages()
                    .stream()
                    .map(imageMapper::toDTO)
                    .toList()
        );
        productDTO.setDescriptionHistories(
            product.getDescriptionHistories()
                    .stream()
                    .map(descriptionHistoryMapper::toDTO)
                    .toList()
        );

        return productDTO;
    }
}
