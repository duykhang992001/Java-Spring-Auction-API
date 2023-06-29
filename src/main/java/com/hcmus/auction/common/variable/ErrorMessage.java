package com.hcmus.auction.common.variable;

public enum ErrorMessage {
    MISSING_PAGE_PARAMETERS("Please provide enough page and size value"),
    MISSING_SORT_PARAMETERS("Please provide enough sort and order value"),
    EXCESS_TIMESTAMP_PARAMETERS("Please provide only lte or gte timestamp or nothing"),
    WRONG_ORDER_BY_PARAMETER("Please provide correct order by value: asc or desc"),
    WRONG_PRODUCT_SORT_BY_PARAMETER("Please provide correct sort by value: endTimestamp, numOfBid or currentPrice"),
    NOT_EXISTED_PRODUCT("This product does not exist!"),
    NOT_EXISTED_USER("This user does not exist!"),
    EXITED_FAVORITE_PRODUCT("This product has already existed in your watch list!"),
    NOT_EXISTED_FAVORITE_PRODUCT("This product has not already been existed in your watch list!"),
    CAN_NOT_UPGRADE_ROLE("Currently, you can not upgrade your role to seller! Maybe you are having a pending request or you are currently the seller");

    ErrorMessage(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return this.message;
    }
}
