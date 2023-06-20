package com.hcmus.auction.common.util;

public class RequestParamUtil {
    private static final String SORT_BY_PRICE_WITHOUT_SEARCH = "currentPrice";
    private static final String SORT_BY_BID_WITHOUT_SEARCH = "numOfBid";
    private static final String SORT_BY_TIMESTAMP_WITHOUT_SEARCH = "endTimestamp";
    private static final String SORT_BY_PRICE_WITH_SEARCH = "current_price";
    private static final String SORT_BY_BID_WITH_SEARCH = "num_of_bid";
    private static final String SORT_BY_TIMESTAMP_WITH_SEARCH = "end_timestamp";

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
        return sortBy == null ||
                sortBy.equals(SORT_BY_TIMESTAMP_WITHOUT_SEARCH) ||
                sortBy.equals(SORT_BY_BID_WITHOUT_SEARCH) ||
                sortBy.equals(SORT_BY_PRICE_WITHOUT_SEARCH);
    }

    public static String formatProductSortByParameter(String sortBy) {
        return switch (sortBy) {
            case SORT_BY_TIMESTAMP_WITHOUT_SEARCH -> SORT_BY_TIMESTAMP_WITH_SEARCH;
            case SORT_BY_BID_WITHOUT_SEARCH -> SORT_BY_BID_WITH_SEARCH;
            case SORT_BY_PRICE_WITHOUT_SEARCH -> SORT_BY_PRICE_WITH_SEARCH;
            default -> null;
        };
    }
}
