package com.hcmus.auction.common.variable;

public enum ErrorMessage {
    MISSING_PAGE_PARAMETERS("Please provide enough page and size value"),
    MISSING_SORT_PARAMETERS("Please provide enough sort and order value"),
    EXCESS_TIMESTAMP_PARAMETERS("Please provide only lte or gte timestamp or nothing"),
    WRONG_ORDER_BY_PARAMETER("Please provide correct order by value: asc or desc"),
    WRONG_PRODUCT_SORT_BY_PARAMETER("Please provide correct sort by value: endTimestamp, numOfBid or currentPrice"),
    NOT_EXISTED_PRODUCT("This product does not exist!"),
    END_TIMESTAMP_LESS_THAN_START_TIMESTAMP("End timestamp is less than start timestamp"),
    BUY_NOW_PRICE_LESS_THAN_CURRENT_PRICE("The buy now price is less than current price"),
    NOT_EXISTED_USER("This user does not exist!"),
    NOT_EXISTED_ACCOUNT("This account does not exist!"),
    NOT_EXISTED_INNER_CATEGORY("This inner category does not exist!"),
    NOT_EXISTED_OUTER_CATEGORY("This outer category does not exist!"),
    EXISTED_EMAIL("This email existed in the system!"),
    EXITED_FAVORITE_PRODUCT("This product has already existed in your watch list!"),
    NOT_EXISTED_FAVORITE_PRODUCT("This product has not already been existed in your watch list!"),
    CAN_NOT_UPGRADE_ROLE("Currently, you can not upgrade your role to seller! Maybe you are having a pending request or you are currently the seller"),
    CAN_NOT_DELETE_CATEGORY_CONTAINING_PRODUCT("Can not delete category containing any products!"),
    NOT_EXISTED_UPGRADE_REQUEST("This upgrade request does not exist!"),
    CAN_NOT_ACCEPT_UPGRADE_REQUEST("This upgrade request can not be accepted!"),
    CAN_NOT_DECLINE_UPGRADE_REQUEST("This upgrade request can not be declined!"),
    CAN_NOT_DOWNGRADE_USER_ROLE("This user is not a seller, so you can not downgrade!");

    ErrorMessage(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return this.message;
    }
}
