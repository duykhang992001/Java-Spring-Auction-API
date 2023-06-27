package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.variable.FavoriteProductRequest;
import com.hcmus.auction.common.variable.SuccessMessage;
import com.hcmus.auction.common.variable.SuccessResponse;
import com.hcmus.auction.controller.definition.UserController;
import com.hcmus.auction.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"User"}, description = "Operations about users")
public class UserControllerImpl implements UserController {
    private final UserServiceImpl userService;

    @PostMapping(value = "/{userId}/favorite")
    @Override
    @ApiOperation(value = "Add new product to favorite list")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Add successfully"), @ApiResponse(code = 400, message = "Add failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> addNewFavoriteProduct(
            @ApiParam(value = "User id needs to add product to favorite list") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Product needs to be added") @RequestBody FavoriteProductRequest favoriteProductRequest) {
        userService.addNewFavoriteProduct(userId, favoriteProductRequest.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessMessage.ADD_FAVORITE_PRODUCT_SUCCESSFULLY.getMessage()));
    }

    @DeleteMapping(value = "/{userId}/favorite")
    @Override
    @ApiOperation(value = "Delete product in favorite list")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Delete successfully"), @ApiResponse(code = 400, message = "Delete failed") })
    public ResponseEntity<SuccessResponse> deleteFavoriteProduct(
            @ApiParam(value = "User id needs to delete product in favorite list") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Product needs to be deleted") @RequestBody FavoriteProductRequest favoriteProductRequest) {
        userService.deleteFavoriteProduct(userId, favoriteProductRequest.getProductId());
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.DELETE_FAVORITE_PRODUCT_SUCCESSFULLY.getMessage()));
    }

    @PostMapping(value = "/{userId}/roles/histories")
    @Override
    @ApiOperation(value = "Send a upgraded role request")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Send successfully"), @ApiResponse(code = 400, message = "Send failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> sendRequestToUpgradeRole(
            @ApiParam(value = "User id needs to send request") @PathVariable(value = "userId") String userId) {
        userService.addNewRoleHistory(userId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.ADD_NEW_ROLE_HISTORY_SUCCESSFULLY.getMessage()));
    }
}
