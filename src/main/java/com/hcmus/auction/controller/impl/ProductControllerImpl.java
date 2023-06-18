package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.util.RequestParamUtil;
import com.hcmus.auction.common.variable.EmptyResponse;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.controller.definition.GenericController;
import com.hcmus.auction.controller.definition.PaginationController;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"Product"}, description = "Operations about products")
public class ProductControllerImpl implements PaginationController<ProductDTO>, GenericController<ProductDTO, String> {
    private final ProductServiceImpl productService;

    @GetMapping
    @Override
    @ApiOperation(value = "Get products with pagination and sorting")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<ProductDTO>> getAll(
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "Order by value: asc or desc") @RequestParam(value = "order_by", required = false) String orderBy,
            @ApiParam(value = "Sort by value: numOfBid, currentPrice or endTimestamp") @RequestParam(value = "sort_by", required = false) String sortBy) throws GenericException {
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
        return ResponseEntity.ok(productService.getAll(page, size, sortBy, orderBy));
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
}
