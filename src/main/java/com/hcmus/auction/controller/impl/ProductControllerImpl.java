package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.variable.EmptyResponse;
import com.hcmus.auction.controller.definition.GenericController;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.service.impl.ProductServiceImpl;
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
@RequestMapping(value = "/products", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"Product"}, description = "Operations about products")
public class ProductControllerImpl implements GenericController<ProductDTO, String> {
    private final ProductServiceImpl productService;

    @GetMapping
    @Override
    @ApiOperation(value = "Get all products")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping(value = "/{productId}")
    @Override
    @ApiOperation(value = "Get product by id", response = ProductDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully") })
    public ResponseEntity<?> getById(
            @ApiParam(value = "Product id needs to be fetched", required = true) @PathVariable("productId") String productId) {
        ProductDTO productDTO = productService.getById(productId);
        return productDTO != null ?
                ResponseEntity.ok(productDTO) :
                ResponseEntity.ok(new EmptyResponse());
    }
}
