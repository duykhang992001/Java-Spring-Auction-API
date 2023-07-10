package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.RequestParamUtil;
import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.common.variable.request.ProductRequest;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.ImageDTO;
import com.hcmus.auction.model.dto.InnerCategoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.mapper.ProductMapper;
import com.hcmus.auction.repository.ProductRepository;
import com.hcmus.auction.service.definition.GenericService;
import com.hcmus.auction.service.definition.PaginationService;
import com.hcmus.auction.service.definition.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements PaginationService<ProductDTO>,
        GenericService<ProductDTO, String>,
        ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AuctionHistoryServiceImpl auctionHistoryService;

    @Autowired
    private DescriptionHistoryServiceImpl descriptionHistoryService;

    @Autowired
    private InnerCategoryServiceImpl innerCategoryService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ImageServiceImpl imageService;

    @Override
    public Page<ProductDTO> getAll(Integer page, Integer size, String sortBy, String orderBy, String keyword, Integer lte, Integer gte) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Integer finalGte = gte == null ? currentTimestamp : gte;
        String sortParam = keyword != null ? RequestParamUtil.formatProductSortByParameter(sortBy) : sortBy;
        Sort sort = sortBy != null && orderBy != null ?
                (orderBy.equals("asc") ? Sort.by(sortParam).ascending() : Sort.by(sortParam).descending()) :
                Sort.unsorted();
        Pageable pageable = page != null && size != null ?
                PageRequest.of(page, size, sort) :
                PageRequest.of(0, Integer.MAX_VALUE, sort);
        Page<Product> productPage = keyword != null ?
                (lte == null ? productRepository.searchWithGteEndTimestamp(keyword, finalGte, pageable) :
                        productRepository.searchWithLteEndTimestamp(keyword, lte, pageable)) :
                (lte == null ? productRepository.findAllByEndTimestampGreaterThanEqual(finalGte, pageable) :
                        productRepository.findAllByEndTimestampLessThanEqual(lte, pageable));
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public ProductDTO getById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(productMapper::toDTO).orElse(null);
    }

    @Override
    public Page<ProductDTO> getProductsByCategoryId(String categoryId, String exclusiveProductId, Integer page, Integer size, Integer lte, Integer gte) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Integer finalGte = gte == null ? currentTimestamp : gte;
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<Product> productPage = exclusiveProductId == null ?
                (lte == null ?
                        productRepository.findAllByCategoryIdAndEndTimestampGreaterThanEqual(categoryId, finalGte, pageable) :
                        productRepository.findAllByCategoryIdAndEndTimestampLessThanEqual(categoryId, lte, pageable)) :
                (lte == null ?
                        productRepository.findAllByCategoryIdAndEndTimestampGreaterThanEqualAndIdNot(categoryId, finalGte, exclusiveProductId, pageable) :
                        productRepository.findAllByCategoryIdAndEndTimestampLessThanEqualAndIdNot(categoryId, lte, exclusiveProductId, pageable));
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public Page<AuctionHistoryDTO> getAuctionHistoriesByProductId(String productId, Integer page, Integer size, String orderBy) {
        return auctionHistoryService.getAuctionHistoriesByProductId(productId, page, size, orderBy);
    }

    @Override
    public Page<ProductDTO> getAuctioningProductsByUserId(String userId, Integer page, Integer size) {
        Page<String> productIdPage = auctionHistoryService.getAuctioningProductIdByUserId(userId, page, size);
        return productIdPage.map(this::getById);
    }

    @Override
    public Page<ProductDTO> getWonProductsByUserId(String userId, Integer page, Integer size) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<Product> productPage = productRepository.findAllByCurrentWinnerIdAndEndTimestampLessThanEqual(userId, currentTimestamp, pageable);
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public Page<ProductDTO> getExpiredOwnProductsByUserId(String userId, Integer page, Integer size) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<Product> productPage = productRepository.findAllByOwnerIdAndEndTimestampLessThanEqual(userId, currentTimestamp, pageable);
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public Page<ProductDTO> getActiveOwnProductsByUserId(String userId, Integer page, Integer size) {
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();
        Pageable pageable = page != null && size != null ? PageRequest.of(page, size) : Pageable.unpaged();
        Page<Product> productPage = productRepository.findAllByOwnerIdAndEndTimestampGreaterThanEqual(userId, currentTimestamp, pageable);
        return productPage.map(productMapper::toDTO);
    }

    @Override
    public void addNewProductDescription(String productId, String content) {
        if (this.getById(productId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_PRODUCT.getMessage());
        descriptionHistoryService.addNewProductDescription(productId, content);
    }

    @Override
    public void addNewProduct(ProductRequest product) {
        if (product.getEndTimestamp() < product.getStartTimestamp())
            throw new GenericException(ErrorMessage.END_TIMESTAMP_LESS_THAN_START_TIMESTAMP.getMessage());

        if (product.getBuyNowPrice() != null && product.getBuyNowPrice() < product.getCurrentPrice())
            throw new GenericException(ErrorMessage.BUY_NOW_PRICE_LESS_THAN_CURRENT_PRICE.getMessage());

        if (innerCategoryService.getById(product.getCategoryId()) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_INNER_CATEGORY.getMessage());

        if (userService.getById(product.getOwnerId()) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());

        String productId = UUID.randomUUID().toString();
        ProductDTO productDTO = new ProductDTO();
        UserDTO owner = new UserDTO();
        InnerCategoryDTO innerCategoryDTO = new InnerCategoryDTO();
        List<ImageDTO> imageDTOs = product.getImages().stream()
                .map(image -> new ImageDTO(
                        UUID.randomUUID().toString(),
                        image.getUrl(),
                        image.getIsThumbnailImage(),
                        productId))
                .toList();

        owner.setId(product.getOwnerId());
        innerCategoryDTO.setId(product.getCategoryId());
        productDTO.setId(productId);
        productDTO.setName(product.getName());
        productDTO.setNumOfBid(0);
        productDTO.setIsAutoExtendTime(product.getIsAutoExtendTime());
        productDTO.setAdditionalPrice(product.getAdditionalPrice());
        productDTO.setStartTimestamp(product.getStartTimestamp());
        productDTO.setEndTimestamp(product.getEndTimestamp());
        productDTO.setBuyNowPrice(product.getBuyNowPrice());
        productDTO.setCurrentPrice(product.getCurrentPrice());
        productDTO.setCurrentWinner(null);
        productDTO.setOwner(owner);
        productDTO.setCategory(innerCategoryDTO);

        productRepository.save(productMapper.toEntity(productDTO));
        descriptionHistoryService.addNewProductDescription(productId, product.getDescription());
        imageService.addListOfImages(imageDTOs);
    }
}
