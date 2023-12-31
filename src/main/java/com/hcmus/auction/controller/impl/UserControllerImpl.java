package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.util.RequestParamUtil;
import com.hcmus.auction.common.variable.ErrorMessage;
import com.hcmus.auction.common.variable.request.FavoriteProductRequest;
import com.hcmus.auction.common.variable.request.ProfileRequest;
import com.hcmus.auction.common.variable.SuccessMessage;
import com.hcmus.auction.common.variable.request.ChangePasswordRequest;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.common.variable.response.UserPointResponse;
import com.hcmus.auction.controller.definition.UserController;
import com.hcmus.auction.exception.GenericException;
import com.hcmus.auction.model.dto.AccountDTO;
import com.hcmus.auction.model.dto.FavoriteProductDTO;
import com.hcmus.auction.model.dto.ProductDTO;
import com.hcmus.auction.model.dto.ReviewDTO;
import com.hcmus.auction.model.dto.RoleHistoryDTO;
import com.hcmus.auction.service.impl.UserServiceImpl;
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

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"User"}, description = "Operations about users")
public class UserControllerImpl implements UserController {
    private final UserServiceImpl userService;

    @GetMapping(value = "/{userId}/favorite")
    @Override
    @ApiOperation(value = "Get favorite list with pagination and timestamp")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<FavoriteProductDTO>> getFavoriteProductsByUserId(
            @ApiParam(value = "User id needs to get favorite list") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "End timestamp product must be less than") @RequestParam(value = "lte", required = false) Integer lte,
            @ApiParam(value = "End timestamp product must be greater than") @RequestParam(value = "gte", required = false) Integer gte) {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        if (!RequestParamUtil.isValidTimestampParameter(lte, gte)) {
            throw new GenericException(ErrorMessage.EXCESS_TIMESTAMP_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(userService.getFavoriteProductsByUserId(userId, page, size, lte, gte));
    }

    @GetMapping(value = "/{userId}/auctioning")
    @Override
    @ApiOperation(value = "Get auctioning product list with pagination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<ProductDTO>> getAuctioningProductsByUserId(
            @ApiParam(value = "User id needs to get auctioning list") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size) {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(userService.getAuctioningProductsByUserId(userId, page, size));
    }

    @GetMapping(value = "/{userId}/won")
    @Override
    @ApiOperation(value = "Get won product list with pagination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<ProductDTO>> getWonProductsByUserId(
            @ApiParam(value = "User id needs to get won list") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size) {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(userService.getWonProductsByUserId(userId, page, size));
    }

    @GetMapping(value = "/{userId}/expired")
    @Override
    @ApiOperation(value = "Get expired own product list with pagination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<ProductDTO>> getExpiredOwnProductsByUserId(
            @ApiParam(value = "User id needs to get expired own products") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size) {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(userService.getExpiredOwnProductsByUserId(userId, page, size));
    }

    @GetMapping(value = "/{userId}/active")
    @Override
    @ApiOperation(value = "Get active own product list with pagination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<ProductDTO>> getActiveOwnProductsByUserId(
            @ApiParam(value = "User id needs to get active own products") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size) {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(userService.getActiveOwnProductsByUserId(userId, page, size));
    }

    @GetMapping(value = "/roles/requests")
    @Override
    @ApiOperation(value = "Get all unaccepted role requests with pagination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<RoleHistoryDTO>> getUnacceptedUpgradeRequests(
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size) {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(userService.getUnacceptedUpgradeRequests(page, size));
    }

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

    @PostMapping(value = "/{userId}/roles/requests/upgrade")
    @Override
    @ApiOperation(value = "Send a upgraded role request")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Send successfully"), @ApiResponse(code = 400, message = "Send failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> sendRequestToUpgradeRole(
            @ApiParam(value = "User id needs to send request") @PathVariable(value = "userId") String userId) {
        userService.addNewRoleHistory(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessMessage.ADD_NEW_ROLE_HISTORY_SUCCESSFULLY.getMessage()));
    }

    @GetMapping(value = "/{userId}/reviews")
    @Override
    @ApiOperation(value = "Get review list with pagination")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<Page<ReviewDTO>> getReviewsByUserId(
            @ApiParam(value = "User id needs to get review list") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Page number") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "Size of each page") @RequestParam(value = "size", required = false) Integer size) {
        if (!RequestParamUtil.isValidPageParameters(page, size)) {
            throw new GenericException(ErrorMessage.MISSING_PAGE_PARAMETERS.getMessage());
        }
        return ResponseEntity.ok(userService.getReviewsByUserId(userId, page, size));
    }

    @GetMapping(value = "/{userId}/points")
    @Override
    @ApiOperation(value = "Get user's point")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<UserPointResponse> getPointsByUserId(
            @ApiParam(value = "User id needs to get points") @PathVariable(value = "userId") String userId) {
        return ResponseEntity.ok(userService.getPointsByUserId(userId));
    }

    @GetMapping(value = "/{userId}/profile")
    @Override
    @ApiOperation(value = "Get user's profile")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get successfully"), @ApiResponse(code = 400, message = "Get failed") })
    public ResponseEntity<AccountDTO> getProfile(
            @ApiParam(value = "User id needs to get profile") @PathVariable(value = "userId") String userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PutMapping(value = "/{userId}/profile")
    @Override
    @ApiOperation(value = "Update user's profile")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Update successfully"), @ApiResponse(code = 400, message = "Update failed") })
    public ResponseEntity<SuccessResponse> updateProfile(
            @ApiParam(value = "User id needs to update profile") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "New profile") @RequestBody ProfileRequest newProfile) {
        userService.updateProfile(userId, newProfile);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.UPDATE_PROFILE_SUCCESSFULLY.getMessage()));
    }

    @PutMapping(value = "/roles/requests/accept/{requestId}")
    @Override
    @ApiOperation(value = "Accept user role upgrade request")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Accept successfully"), @ApiResponse(code = 400, message = "Accept failed") })
    public ResponseEntity<SuccessResponse> acceptUpgradeRequest(
            @ApiParam(value = "Request id needs to accepted") @PathVariable(value = "requestId") String requestId) {
        userService.acceptUpgradeRequest(requestId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.ACCEPT_UPGRADE_ROLE_SUCCESSFULLY.getMessage()));
    }

    @PutMapping(value = "/roles/requests/decline/{requestId}")
    @Override
    @ApiOperation(value = "Decline user role upgrade request")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Decline successfully"), @ApiResponse(code = 400, message = "Decline failed") })
    public ResponseEntity<SuccessResponse> declineUpgradeRequest(
            @ApiParam(value = "Request id needs to declined") @PathVariable(value = "requestId") String requestId) {
        userService.declineUpgradeRequest(requestId);
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.DECLINE_UPGRADE_ROLE_SUCCESSFULLY.getMessage()));
    }

    @PostMapping(value = "/{userId}/roles/requests/downgrade")
    @Override
    @ApiOperation(value = "Downgrade a user from seller to bidder")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Downgrade successfully"), @ApiResponse(code = 400, message = "Downgrade failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SuccessResponse> downgradeUserRole(
            @ApiParam(value = "User id needs to downgrade") @PathVariable(value = "userId") String userId) {
        userService.downgradeUserRole(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessMessage.DOWNGRADE_USER_ROLE_SUCCESSFULLY.getMessage()));
    }

    @PutMapping(value = "/{userId}/password")
    @Override
    @ApiOperation(value = "Change user password")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Change successfully"), @ApiResponse(code = 400, message = "Change failed") })
    public ResponseEntity<SuccessResponse> changePassword(
            @ApiParam(value = "User id needs to changed password") @PathVariable(value = "userId") String userId,
            @ApiParam(value = "Password info") @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(userId, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.CHANGE_PASSWORD_SUCCESSFULLY.getMessage()));
    }
}
