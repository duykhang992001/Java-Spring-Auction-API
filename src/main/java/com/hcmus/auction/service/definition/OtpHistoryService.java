package com.hcmus.auction.service.definition;

public interface OtpHistoryService {
    void addNewOtpForRegistration(String value, String userId);
    void verifyRegistrationOtpCode(String userId, String otpCode);
}
