package com.hcmus.auction.service.definition;

public interface AuthService {
    String signUpNewAccount(String email, String password, String name, String address);
    void verifyRegistrationOtpCode(String userId, String otpCode);
    String sendForgotPasswordOtpViaEmail(String email);
    String verifyForgotPasswordOtpCode(String userId, String otpCode);
    void resetPassword(String token, String userId, String password);
}
