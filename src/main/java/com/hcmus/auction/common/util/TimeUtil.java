package com.hcmus.auction.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtil {
    public static Integer getCurrentTimestamp() {
        final int VN_UTC = 7 * 3600;
        return (int) LocalDateTime.now().toEpochSecond(ZoneOffset.ofTotalSeconds(VN_UTC));
    }
}
