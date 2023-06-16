package com.hcmus.auction.common.util;

public class PageUtil {
    public static boolean isValidPageParameters(Integer page, Integer size) {
        return (page == null && size != null) || (page != null && size == null);
    }
}
