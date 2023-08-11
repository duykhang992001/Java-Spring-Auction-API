package com.hcmus.auction.controller.definition;

import com.hcmus.auction.common.variable.request.OtpRequest;
import com.hcmus.auction.common.variable.request.SignUpRequest;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.common.variable.response.UserIdResponse;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<UserIdResponse> signUpNewAccount(SignUpRequest signUpRequest);
    ResponseEntity<SuccessResponse> verifyRegistrationOtpCode(OtpRequest otpRequest);
}
