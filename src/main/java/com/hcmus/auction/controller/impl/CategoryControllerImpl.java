package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.variable.EmptyResponse;
import com.hcmus.auction.controller.definition.GenericController;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.service.impl.CategoryServiceImpl;
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
@RequestMapping(value = "/categories", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"Category"}, description = "Operations about categories")
public class CategoryControllerImpl implements GenericController<OuterCategoryDTO, String> {
    private final CategoryServiceImpl categoryService;

    @GetMapping
    @Override
    @ApiOperation(value = "Get categories with pagination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<OuterCategoryDTO>> getAll(
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size) throws GenericException {
        if ((page == null && size != null) || (page != null && size == null)) {
            throw new GenericException("Please provide enough page and size value");
        }
        return ResponseEntity.ok(categoryService.getAll(page, size));
    }

    @GetMapping(value = "/{categoryId}")
    @Override
    @ApiOperation(value = "Get category by id", response = OuterCategoryDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<?> getById(
            @ApiParam(value = "Outer category id needs to be fetched", required = true) @PathVariable(value = "categoryId") String categoryId) {
        OuterCategoryDTO outerCategoryDTO = categoryService.getById(categoryId);
        return outerCategoryDTO != null ?
                ResponseEntity.ok(outerCategoryDTO) :
                ResponseEntity.ok(new EmptyResponse());
    }
}
