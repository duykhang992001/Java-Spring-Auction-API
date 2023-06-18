package com.hcmus.auction.common.variable;

public enum ErrorMessage {
    MISSING_PAGE_PARAMETERS("Please provide enough page and size value"),
    MISSING_SORT_PARAMETERS("Please provide enough sort and order value"),
    WRONG_ORDER_BY_PARAMETER("Please provide correct order by value: asc or desc"),
    WRONG_PRODUCT_SORT_BY_PARAMETER("Please provide correct sort by value: endTimestamp, numOfBid or currentPrice");

    ErrorMessage(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return this.message;
    }
}
