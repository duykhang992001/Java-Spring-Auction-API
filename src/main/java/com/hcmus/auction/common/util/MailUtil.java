package com.hcmus.auction.common.util;

public class MailUtil {
    public static String getEmailBodyWhenReceivingOtp(String fullName, String otpCode) {
        return "Dear " + fullName + ",\n"
                + "Here is the OTP code you need to reset password: " + otpCode + ".\n"
                + "This code will be expired 5 minutes after this email was sent. If you did not make this request, you can ignore this email.";
    }
}
