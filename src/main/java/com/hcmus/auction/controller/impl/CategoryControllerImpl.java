package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.util.RequestParamUtil;
import com.hcmus.auction.common.variable.response.EmptyResponse;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.controller.definition.CategoryController;
import com.hcmus.auction.controller.definition.GenericController;
import com.hcmus.auction.controller.definition.UnPaginationController;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.service.impl.OuterCategoryServiceImpl;
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

import java.util.List;

@RestController
@RequestMapping(value = "/categories", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"Category"}, description = "Operations about categories")
public class CategoryControllerImpl implements UnPaginationController<OuterCategoryDTO>,
        GenericController<OuterCategoryDTO, String>,
        CategoryController {
    private final OuterCategoryServiceImpl categoryService;

    @GetMapping
    @Override
    @ApiOperation(value = "Get categories")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<List<OuterCategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping(value = "/{categoryId}")
    @Override
    @ApiOperation(value = "Get category by outer category id", response = OuterCategoryDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<?> getById(
            @ApiParam(value = "Outer category id needs to be fetched", required = true) @PathVariable(value = "categoryId") String categoryId) {
        OuterCategoryDTO outerCategoryDTO = categoryService.getById(categoryId);
        return outerCategoryDTO != null ?
                ResponseEntity.ok(outerCategoryDTO) :
                ResponseEntity.ok(new EmptyResponse());
    }

    @GetMapping(value = "/{categoryId}/products")
    @Override
    @ApiOperation(value = "Get products by inner category id with pagination, exclusive product id and end timestamp")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<ProductDTO>> getProductsByCategoryId(
            @ApiParam(value = "Inner category id needs to be fetched", required = true) @PathVariable(value = "categoryId") String categoryId,
            @ApiParam(value = "Product id must not be included") @RequestParam(value = "exclusiveProductId", required = false) String exclusiveProductId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "End timestamp product must be less than") @RequestParam(value = "lte", required = false) Integer lte,
            @ApiParam(value = "End timestamp product must be greater than") @RequestParam(value = "gte", required = false) Integer gte) throws GenericException {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        if (!RequestParamUtil.isValidTimestampParameter(lte, gte)) {
            throw new GenericException(ErrorMessage.EXCESS_TIMESTAMP_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(categoryService.getProductsByCategoryId(categoryId, exclusiveProductId, page, size, lte, gte));
    }
}
