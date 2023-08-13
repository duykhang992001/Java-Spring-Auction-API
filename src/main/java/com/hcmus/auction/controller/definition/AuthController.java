package com.hcmus.auction.controller.definition;

import com.hcmus.auction.common.variable.request.EmailRequest;
import com.hcmus.auction.common.variable.request.OtpRequest;
import com.hcmus.auction.common.variable.request.ResetPasswordRequest;
import com.hcmus.auction.common.variable.request.SignUpRequest;
import com.hcmus.auction.common.variable.response.OtpTokenResponse;
import com.hcmus.auction.common.variable.response.SuccessResponse;
import com.hcmus.auction.common.variable.response.UserIdResponse;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<UserIdResponse> signUpNewAccount(SignUpRequest signUpRequest);
    ResponseEntity<SuccessResponse> verifyRegistrationOtpCode(OtpRequest otpRequest);
    ResponseEntity<UserIdResponse> sendForgotPasswordOtpViaEmail(EmailRequest emailRequest);
    ResponseEntity<OtpTokenResponse> verifyForgotPasswordOtpCode(OtpRequest otpRequest);
    ResponseEntity<SuccessResponse> resetPassword(ResetPasswordRequest resetPasswordRequest);
}
