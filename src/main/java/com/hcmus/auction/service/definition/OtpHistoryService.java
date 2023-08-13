package com.hcmus.auction.service.definition;

import com.hcmus.auction.model.entity.OtpHistory;

public interface OtpHistoryService {
    void addNewOtp(String value, String userId, Boolean isUsedForSignUp);
    String verifyOtpCode(String userId, String otpCode, Boolean isUsedForSignUp);
    boolean isValidOtpToken(String token, String userId);
    OtpHistory getLastOtpRecordByUserIdAndType(String userId, Boolean isUsedForSignUp);
}
