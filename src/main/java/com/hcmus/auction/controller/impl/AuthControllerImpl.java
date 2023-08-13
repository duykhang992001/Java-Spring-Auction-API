package com.hcmus.auction.controller.impl;

import com.hcmus.auction.common.variable.SuccessMessage;
import com.hcmus.auction.common.variable.request.EmailRequest;
import com.hcmus.auction.common.variable.request.OtpRequest;
import com.hcmus.auction.common.variable.request.ResetPasswordRequest;
import com.hcmus.auction.common.variable.request.SignUpRequest;
import com.hcmus.auction.common.variable.response.OtpTokenResponse;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.common.variable.response.UserIdResponse;
import com.hcmus.auction.controller.definition.AuthController;
import com.hcmus.auction.service.impl.AuthServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@AllArgsConstructor
@Api(tags = {"Authentication"}, description = "Operations about authentication")
public class AuthControllerImpl implements AuthController {
    private final AuthServiceImpl authService;

    @PostMapping(value = "/registration")
    @Override
    @ApiOperation(value = "Sign up a new account")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Send OTP successfully"), @ApiResponse(code = 400, message = "Send OTP failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserIdResponse> signUpNewAccount(
            @ApiParam(value = "User info") @RequestBody SignUpRequest signUpRequest) {
        String userId = authService.signUpNewAccount(signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getName(), signUpRequest.getAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserIdResponse(userId));
    }

    @PutMapping(value = "/registration/otp/verification")
    @Override
    @ApiOperation(value = "Verify otp code when signing up")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Verify successfully"), @ApiResponse(code = 400, message = "Verify failed") })
    public ResponseEntity<SuccessResponse> verifyRegistrationOtpCode(
            @ApiParam(value = "Otp info") @RequestBody OtpRequest otpRequest) {
        authService.verifyRegistrationOtpCode(otpRequest.getUserId(), otpRequest.getOtpCode());
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.VERIFY_OTP_SUCCESSFULLY.getMessage()));
    }

    @PostMapping(value = "/password/otp")
    @Override
    @ApiOperation(value = "Send OTP when user forget password")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Send OTP successfully"), @ApiResponse(code = 400, message = "Send OTP failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserIdResponse> sendForgotPasswordOtpViaEmail(
            @ApiParam(value = "Email info") @RequestBody EmailRequest emailRequest) {
        String userId = authService.sendForgotPasswordOtpViaEmail(emailRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserIdResponse(userId));
    }

    @PostMapping(value = "/password/otp/verification")
    @Override
    @ApiOperation(value = "Verify otp code when forgetting password")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Verify successfully"), @ApiResponse(code = 400, message = "Verify failed") })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<OtpTokenResponse> verifyForgotPasswordOtpCode(
            @ApiParam(value = "Otp info") @RequestBody OtpRequest otpRequest) {
        String token = authService.verifyForgotPasswordOtpCode(otpRequest.getUserId(), otpRequest.getOtpCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(new OtpTokenResponse(token));
    }

    @PutMapping(value = "/password")
    @Override
    @ApiOperation(value = "Reset password")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Reset successfully"), @ApiResponse(code = 400, message = "Reset failed") })
    public ResponseEntity<SuccessResponse> resetPassword(
            @ApiParam(value = "Password info") @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getUserId(), resetPasswordRequest.getPassword());
        return ResponseEntity.ok(new SuccessResponse(SuccessMessage.RESET_PASSWORD_SUCCESSFULLY.getMessage()));
    }
}
