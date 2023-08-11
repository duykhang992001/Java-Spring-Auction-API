package com.hcmus.auction.common.util;

import java.text.DecimalFormat;
import java.util.Random;

public class OtpUtil {
    private static final int MAX_OTP = 999999;
    private static final String OTP_PATTERN = "000000";

    public static String createOtpCode() {
        return new DecimalFormat(OTP_PATTERN).format(new Random().nextInt(MAX_OTP));
    }
}
