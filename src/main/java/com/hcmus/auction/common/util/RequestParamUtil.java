package com.hcmus.auction.common.util;

public class RequestParamUtil {
    public static boolean isValidPageParameters(Integer page, Integer size) {
        return (page == null && size == null) || (page != null && size != null);
    }

    public static boolean isValidSortParameters(String sortBy, String orderBy) {
        return (sortBy == null && orderBy == null) || (sortBy != null && orderBy != null);
    }

    public static boolean isValidOrderByParameter(String orderBy) {
        return orderBy == null || orderBy.equals("asc") || orderBy.equals("desc");
    }

    public static boolean isValidProductSortByParameter(String sortBy) {
        return sortBy == null || sortBy.equals("endTimestamp") || sortBy.equals("numOfBid") || sortBy.equals("currentPrice");
    }
}
