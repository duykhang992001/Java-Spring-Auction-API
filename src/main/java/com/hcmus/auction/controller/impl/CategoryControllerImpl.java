package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.variable.EmptyResponse;
import com.hcmus.auction.controller.definition.GenericController;
import com.hcmus.auction.model.dto.OuterCategoryDTO;
import com.hcmus.auction.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"Category"}, description = "Operations about categories")
public class CategoryControllerImpl implements GenericController<OuterCategoryDTO, String> {
    private final CategoryServiceImpl categoryService;

    @GetMapping
    @Override
    @ApiOperation(value = "Get all categories")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<List<OuterCategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping(value = "/{categoryId}")
    @Override
    @ApiOperation(value = "Get category by id", response = OuterCategoryDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<?> getById(
            @ApiParam(value = "Category id needs to be fetched", required = true) @PathVariable(value = "categoryId") String categoryId) {
        OuterCategoryDTO outerCategoryDTO = categoryService.getById(categoryId);
        return outerCategoryDTO != null ?
                ResponseEntity.ok(outerCategoryDTO) :
                ResponseEntity.ok(new EmptyResponse());
    }
}
