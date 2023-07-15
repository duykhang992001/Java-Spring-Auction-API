package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.util.RequestParamUtil;
import com.hcmus.auction.common.variable.SuccessMessage;
import com.hcmus.auction.common.variable.request.CategoryRequest;
import com.hcmus.auction.common.variable.response.EmptyResponse;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.controller.definition.CategoryController;
import com.hcmus.auction.controller.definition.GenericController;
import com.hcmus.auction.controller.definition.UnPaginationController;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.service.impl.InnerCategoryServiceImpl;
import com.hcmus.auction.service.impl.OuterCategoryServiceImpl;
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
@RequestMapping(value = "/categories", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"Category"}, description = "Operations about categories")
public class CategoryControllerImpl implements UnPaginationController<OuterCategoryDTO>,
        GenericController<OuterCategoryDTO, String>,
        CategoryController {
    private final OuterCategoryServiceImpl outerCategoryService;
    private final InnerCategoryServiceImpl innerCategoryService;

    @GetMapping
    @Override
    @ApiOperation(value = "Get categories")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<List<OuterCategoryDTO>> getAll() {
        return ResponseEntity.ok(outerCategoryService.getAll());
    }

    @GetMapping(value = "/{categoryId}")
    @Override
    @ApiOperation(value = "Get category by outer category id", response = OuterCategoryDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<?> getById(
            @ApiParam(value = "Outer category id needs to be fetched", required = true) @PathVariable(value = "categoryId") String categoryId) {
        OuterCategoryDTO outerCategoryDTO = outerCategoryService.getById(categoryId);
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
        return ResponseEntity.ok(innerCategoryService.getProductsByCategoryId(categoryId, exclusiveProductId, page, size, lte, gte));
    }

    @PostMapping(value = "/{categoryId}")
    @Override
    @ApiOperation(value = "Add new inner category")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Add successfully"), @ApiResponse(code = 400, message = "Add failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> addNewInnerCategory(
            @ApiParam(value = "Outer category id contains inner category") @PathVariable(value = "categoryId") String outerCategoryId,
            @ApiParam(value = "Inner category needs to be added") @RequestBody CategoryRequest categoryRequest) {
        innerCategoryService.addNewInnerCategory(outerCategoryId, categoryRequest.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessMessage.ADD_NEW_INNER_CATEGORY_SUCCESSFULLY.getMessage()));
    }

    @PostMapping
    @Override
    @ApiOperation(value = "Add new outer category")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Add successfully"), @ApiResponse(code = 400, message = "Add failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> addNewOuterCategory(
            @ApiParam(value = "Outer category needs to be added") @RequestBody CategoryRequest categoryRequest) {
        outerCategoryService.addNewOuterCategory(categoryRequest.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessMessage.ADD_NEW_OUTER_CATEGORY_SUCCESSFULLY.getMessage()));
    }

    @PutMapping(value = "/inner/{categoryId}")
    @Override
    @ApiOperation(value = "Update inner category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Update successfully"), @ApiResponse(code = 400, message = "Update failed") })
    public ResponseEntity<SuccessResponse> updateInnerCategoryById(
            @ApiParam(value = "Inner category id needs to update") @PathVariable(value = "categoryId") String innerCategoryId,
            @ApiParam(value = "New inner category") @RequestBody CategoryRequest categoryRequest) {
        innerCategoryService.updateInnerCategoryById(innerCategoryId, categoryRequest.getName());
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.UPDATE_INNER_CATEGORY_SUCCESSFULLY.getMessage()));
    }

    @PutMapping(value = "/outer/{categoryId}")
    @Override
    @ApiOperation(value = "Update outer category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Update successfully"), @ApiResponse(code = 400, message = "Update failed") })
    public ResponseEntity<SuccessResponse> updateOuterCategoryById(
            @ApiParam(value = "Outer category id needs to update") @PathVariable(value = "categoryId") String outerCategoryId,
            @ApiParam(value = "New outer category") @RequestBody CategoryRequest categoryRequest) {
        outerCategoryService.updateOuterCategoryById(outerCategoryId, categoryRequest.getName());
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.UPDATE_OUTER_CATEGORY_SUCCESSFULLY.getMessage()));
    }

    @DeleteMapping(value = "/inner/{categoryId}")
    @Override
    @ApiOperation(value = "Delete inner category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Delete successfully"), @ApiResponse(code = 400, message = "Delete failed") })
    public ResponseEntity<SuccessResponse> deleteInnerCategoryById(
            @ApiParam(value = "Inner category id needs to delete") @PathVariable(value = "categoryId") String innerCategoryId) {
        innerCategoryService.deleteInnerCategoryById(innerCategoryId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.DELETE_INNER_CATEGORY_SUCCESSFULLY.getMessage()));
    }

    @DeleteMapping(value = "/outer/{categoryId}")
    @Override
    @ApiOperation(value = "Delete outer category")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Delete successfully"), @ApiResponse(code = 400, message = "Delete failed") })
    public ResponseEntity<SuccessResponse> deleteOuterCategoryById(
            @ApiParam(value = "Outer category id needs to delete") @PathVariable(value = "categoryId") String outerCategoryId) {
        outerCategoryService.deleteOuterCategoryById(outerCategoryId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.DELETE_OUTER_CATEGORY_SUCCESSFULLY.getMessage()));
    }
}
