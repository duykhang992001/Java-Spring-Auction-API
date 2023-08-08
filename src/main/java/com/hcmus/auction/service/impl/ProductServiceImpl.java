package com.hcmus.auction.service.impl;

import com.hcmus.auction.common.util.RequestParamUtil;
import com.hcmus.auction.common.util.TimeUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.common.variable.SuccessMessage;
import com.hcmus.auction.common.variable.request.ProductRequest;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.AuctionRequestDTO;
import com.hcmus.auction.model.dto.ImageDTO;
import com.hcmus.auction.model.dto.InnerCategoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.UserDTO;
import com.hcmus.auction.model.entity.Product;
import com.hcmus.auction.model.entity.User;
import com.hcmus.auction.model.mapper.ProductMapper;
import com.hcmus.auction.model.mapper.UserMapper;
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

    @Autowired
    private AuctionRequestServiceImpl auctionRequestService;

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

    @Override
    public void deleteProductById(String productId) {
        if (this.getById(productId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_PRODUCT.getMessage());
        productRepository.deleteById(productId);
    }

    @Override
    public SuccessResponse auctionProduct(String productId, String userId, Integer price) {
        final double ACCEPTED_PERCENT_FOR_AUCTIONING = 0.8;
        final int DIFF_5_MINUTES_IN_SECONDS = 5 * 60;
        final int CAN_NOT_SEND_REQUEST = 0;
        final int CAN_SEND_REQUEST = 1;
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.orElse(null);
        UserDTO user = userService.getById(userId);
        Integer currentTimestamp = TimeUtil.getCurrentTimestamp();

        if (product == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_PRODUCT.getMessage());
        if (user == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_USER.getMessage());
        if (product.getEndTimestamp() < currentTimestamp)
            throw new GenericException(ErrorMessage.PRODUCT_EXPIRED.getMessage());
        if (product.getOwner().getId().equals(userId))
            throw new GenericException(ErrorMessage.AUCTIONING_USER_CAN_NOT_BE_OWNER.getMessage());
        if (product.getCurrentWinner().getId().equals(userId))
            throw new GenericException(ErrorMessage.AUCTIONING_USER_CAN_NOT_BE_WINNER.getMessage());
        if (user.getNumOfLike() != 0 || user.getNumOfDislike() != 0) {
            if (user.getNumOfLike() * 1.0 / (user.getNumOfLike() + user.getNumOfDislike()) < ACCEPTED_PERCENT_FOR_AUCTIONING)
                throw new GenericException(ErrorMessage.NOT_ENOUGH_AUCTIONING_PERCENT.getMessage());
        } else {
            if (auctionRequestService.getRequestStatus(userId, productId) == CAN_SEND_REQUEST) {
                auctionRequestService.addNewAuctionRequest(userId, productId);
                return new SuccessResponse(SuccessMessage.SEND_AUCTION_REQUEST_SUCCESSFULLY.getMessage());
            } else if (auctionRequestService.getRequestStatus(userId, productId) == CAN_NOT_SEND_REQUEST)
                throw new GenericException(ErrorMessage.CAN_NOT_SEND_AUCTION_REQUEST.getMessage());
        }
        if (price <= product.getCurrentPrice() || price > product.getBuyNowPrice() ||
                ((price - product.getCurrentPrice()) % product.getAdditionalPrice() != 0))
            throw new GenericException(ErrorMessage.AUCTIONING_PRICE_INVALID.getMessage());
        if (!auctionHistoryService.isAbleToAuction(userId, productId))
            throw new GenericException(ErrorMessage.REJECTED_USER.getMessage());

        User bidder = new User();

        bidder.setId(userId);
        product.setCurrentPrice(price);
        product.setNumOfBid(product.getNumOfBid() + 1);
        product.setCurrentWinner(bidder);
        if (product.getIsAutoExtendTime() && product.getEndTimestamp() - currentTimestamp <= DIFF_5_MINUTES_IN_SECONDS)
            product.setEndTimestamp(product.getEndTimestamp() + DIFF_5_MINUTES_IN_SECONDS * 2);

        productRepository.save(product);
        auctionHistoryService.addNewAuctionHistory(userId, productId, price);

        return new SuccessResponse(SuccessMessage.AUCTION_SUCCESSFULLY.getMessage());
    }

    @Override
    public void declineAuctionHistory(String auctionId) {
        List<String> auctionInfo = auctionHistoryService.declineAuctionHistory(auctionId);
        String userId = auctionInfo.get(0);
        String productId = auctionInfo.get(1);
        String numOfDecreasedBid = auctionInfo.get(2);
        System.out.println(numOfDecreasedBid);
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product = productOptional.get();

        if (product.getCurrentWinner().getId().equals(userId)) {
            final int PAGE = 0;
            final int SIZE = 1;
            UserMapper userMapper = new UserMapper();
            Page<AuctionHistoryDTO> auctionHistoryDTOPage = auctionHistoryService.getAuctionHistoriesByProductId(productId, PAGE, SIZE, null);
            List<AuctionHistoryDTO> auctionHistoryDTOs = auctionHistoryDTOPage.getContent();
            product.setCurrentPrice(auctionHistoryDTOs.get(0).getPrice());
            product.setCurrentWinner(userMapper.toEntity(auctionHistoryDTOs.get(0).getBidder()));
        }

        product.setNumOfBid(product.getNumOfBid() - Integer.parseInt(numOfDecreasedBid));
        productRepository.save(product);
    }

    @Override
    public Page<AuctionRequestDTO> getUnacceptedAuctionRequestsByProductId(String productId, Integer page, Integer size) {
        if (this.getById(productId) == null)
            throw new GenericException(ErrorMessage.NOT_EXISTED_PRODUCT.getMessage());
        return auctionRequestService.getUnacceptedAuctionRequestsByProductId(productId, page, size);
    }

    @Override
    public void acceptAuctionRequest(String requestId) {
        auctionRequestService.acceptAuctionRequest(requestId);
    }

    @Override
    public void declineAuctionRequest(String requestId) {
        auctionRequestService.declineAuctionRequest(requestId);
    }
}
