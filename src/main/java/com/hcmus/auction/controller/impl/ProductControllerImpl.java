package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.util.RequestParamUtil;
import com.hcmus.auction.common.variable.request.AuctionRequest;
import com.hcmus.auction.common.variable.request.DescriptionHistoryRequest;
import com.hcmus.auction.common.variable.request.ProductRequest;
import com.hcmus.auction.common.variable.request.ReviewRequest;
import com.hcmus.auction.common.variable.response.EmptyResponse;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.common.variable.SuccessMessage;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.controller.definition.GenericController;
import com.hcmus.auction.controller.definition.PaginationController;
import com.hcmus.auction.controller.definition.ProductController;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AuctionHistoryDTO;
import com.hcmus.auction.model.dto.AuctionRequestDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.ReviewDTO;
import com.hcmus.auction.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"Product"}, description = "Operations about products")
public class ProductControllerImpl implements PaginationController<ProductDTO>,
        GenericController<ProductDTO, String>,
        ProductController {
    private final ProductServiceImpl productService;

    @GetMapping
    @Override
    @ApiOperation(value = "Get products with pagination, sorting, searching and end timestamp")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<ProductDTO>> getAll(
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "Order by value: asc or desc") @RequestParam(value = "order_by", required = false) String orderBy,
            @ApiParam(value = "Sort by value: numOfBid, currentPrice or endTimestamp") @RequestParam(value = "sort_by", required = false) String sortBy,
            @ApiParam(value = "Search by name or category. If the string has white space, please replace it by %20") @RequestParam(value = "q", required = false) String q,
            @ApiParam(value = "End timestamp product must be greater than") @RequestParam(value = "gte", required = false) Integer gte,
            @ApiParam(value = "End timestamp product must be less than") @RequestParam(value = "lte", required = false) Integer lte) throws GenericException {
        final String WHITE_SPACE = " ";
        final String WHITE_SPACE_IN_PARAM = "%20";

        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        if (!RequestParamUtil.isValidSortParameters(sortBy, orderBy)) {
            throw new GenericException(ErrorMessage.MISSING_SORT_PARAMETERS.getMessage());
        }
        if (!RequestParamUtil.isValidProductSortByParameter(sortBy)) {
            throw new GenericException(ErrorMessage.WRONG_PRODUCT_SORT_BY_PARAMETER.getMessage());
        }
        if (!RequestParamUtil.isValidOrderByParameter(orderBy)) {
            throw new GenericException(ErrorMessage.WRONG_ORDER_BY_PARAMETER.getMessage());
        }
        if (!RequestParamUtil.isValidTimestampParameter(lte, gte)) {
            throw new GenericException(ErrorMessage.EXCESS_TIMESTAMP_PARAMETERS.getMessage());
        }
        String keyword = q == null ? null : q.replaceAll(WHITE_SPACE_IN_PARAM, WHITE_SPACE);
        return ResponseEntity.ok(productService.getAll(page, size, sortBy, orderBy, keyword, lte, gte));
    }

    @GetMapping(value = "/{productId}/histories")
    @Override
    @ApiOperation(value = "Get auction histories by product id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<AuctionHistoryDTO>> getAuctionHistoriesByProductId(
            @ApiParam(value = "Product id needs to be fetched", required = true) @PathVariable(value = "productId") String productId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "Order by value: asc or desc") @RequestParam(value = "order_by", required = false) String orderBy) throws GenericException {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        if (!RequestParamUtil.isValidOrderByParameter(orderBy)) {
            throw new GenericException(ErrorMessage.WRONG_ORDER_BY_PARAMETER.getMessage());
        }
        return ResponseEntity.ok(productService.getAuctionHistoriesByProductId(productId, page, size, orderBy));
    }

    @GetMapping(value = "/{productId}")
    @Override
    @ApiOperation(value = "Get product by id", response = ProductDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<?> getById(
            @ApiParam(value = "Product id needs to be fetched", required = true) @PathVariable(value = "productId") String productId) {
        ProductDTO productDTO = productService.getById(productId);
        return productDTO != null ?
                ResponseEntity.ok(productDTO) :
                ResponseEntity.ok(new EmptyResponse());
    }

    @PostMapping(value = "/{productId}/descriptions")
    @Override
    @ApiOperation(value = "Add new product description")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Add successfully"), @ApiResponse(code = 400, message = "Add failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> addNewProductDescription(
            @ApiParam(value = "Product id needs to be added description") @PathVariable(value = "productId") String productId,
            @ApiParam(value = "Description needs to be added") @RequestBody DescriptionHistoryRequest descriptionHistoryRequest) {
        productService.addNewProductDescription(productId, descriptionHistoryRequest.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessMessage.ADD_NEW_PRODUCT_DESCRIPTION_SUCCESSFULLY.getMessage()));
    }

    @PostMapping
    @Override
    @ApiOperation(value = "Add new product")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Add successfully"), @ApiResponse(code = 400, message = "Add failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> addNewProduct(
            @RequestBody ProductRequest product) {
        productService.addNewProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessMessage.ADD_NEW_PRODUCT_SUCCESSFULLY.getMessage()));
    }

    @DeleteMapping(value = "/{productId}")
    @Override
    @ApiOperation(value = "Delete product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Delete successfully"), @ApiResponse(code = 400, message = "Delete failed") })
    public ResponseEntity<SuccessResponse> deleteProductById(
            @ApiParam(value = "Product id needs to be deleted") @PathVariable(value = "productId") String productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.DELETE_PRODUCT_SUCCESSFULLY.getMessage()));
    }

    @PostMapping(value = "/{productId}/auction")
    @Override
    @ApiOperation(value = "Auction product")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Auction successfully"), @ApiResponse(code = 400, message = "Auction failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> auctionProduct(
            @ApiParam(value = "Product id needs to be auctioned") @PathVariable(value = "productId") String productId,
            @ApiParam(value = "Auction info") @RequestBody AuctionRequest auctionRequest) {
        SuccessResponse response = productService.auctionProduct(productId, auctionRequest.getUserId(), auctionRequest.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/auction/decline/{auctionId}")
    @Override
    @ApiOperation(value = "Decline a user auction turn")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Decline successfully"), @ApiResponse(code = 400, message = "Decline failed") })
    public ResponseEntity<SuccessResponse> declineAuctionHistory(
            @ApiParam(value = "Auction id needs to be declined") @PathVariable(value = "auctionId") String auctionId) {
        productService.declineAuctionHistory(auctionId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.DECLINE_AUCTION_TURN_SUCCESSFULLY.getMessage()));
    }

    @GetMapping(value = "/{productId}/auction/requests")
    @Override
    @ApiOperation(value = "Get unaccepted auction requests with pagination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<AuctionRequestDTO>> getUnacceptedAuctionRequestsByProductId(
            @ApiParam(value = "Product id needs to be got unaccepted auction requests") @PathVariable(value = "productId") String productId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size) {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(productService.getUnacceptedAuctionRequestsByProductId(productId, page, size));
    }

    @PutMapping(value = "/auction/requests/accept/{requestId}")
    @Override
    @ApiOperation(value = "Accept user auction request")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Accept successfully"), @ApiResponse(code = 400, message = "Accept failed") })
    public ResponseEntity<SuccessResponse> acceptAuctionRequest(
            @ApiParam(value = "Request id needs to accepted") @PathVariable(value = "requestId") String requestId) {
        productService.acceptAuctionRequest(requestId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.ACCEPT_AUCTION_REQUEST_SUCCESSFULLY.getMessage()));
    }

    @PutMapping(value = "/auction/request/decline/{requestId}")
    @Override
    @ApiOperation(value = "Decline user auction request")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Accept successfully"), @ApiResponse(code = 400, message = "Accept failed") })
    public ResponseEntity<SuccessResponse> declineAuctionRequest(
            @ApiParam(value = "Request id needs to declined") @PathVariable(value = "requestId") String requestId) {
        productService.declineAuctionRequest(requestId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.DECLINE_AUCTION_REQUEST_SUCCESSFULLY.getMessage()));
    }

    @PostMapping(value = "/{productId}/winner/reviews")
    @Override
    @ApiOperation(value = "Send a product review by winner")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Send successfully"), @ApiResponse(code = 400, message = "Send failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> reviewProductByBidder(
            @ApiParam(value = "Product id needs to added review") @PathVariable(value = "productId") String productId,
            @ApiParam(value = "Review info") @RequestBody ReviewRequest review) {
        productService.reviewProductByBidder(productId, review.getSenderId(), review.getComment(), review.getIsLiked());
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.SEND_REVIEW_SUCCESSFULLY.getMessage()));
    }

    @PostMapping(value = "/{productId}/owner/reviews")
    @Override
    @ApiOperation(value = "Send a product review by owner")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Send successfully"), @ApiResponse(code = 400, message = "Send failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> reviewProductByOwner(
            @ApiParam(value = "Product id needs to added review") @PathVariable(value = "productId") String productId,
            @ApiParam(value = "Review info") @RequestBody ReviewRequest review) {
        productService.reviewProductByOwner(productId, review.getSenderId(), review.getComment(), review.getIsLiked());
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.SEND_REVIEW_SUCCESSFULLY.getMessage()));
    }

    @GetMapping(value = "/{productId}/reviews")
    @Override
    @ApiOperation(value = "Get reviews by product id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(
            @ApiParam(value = "Product id needs to got reviews") @PathVariable(value = "productId") String productId) {
        return ResponseEntity.ok(productService.getReviewsByProductId(productId));
    }
}
