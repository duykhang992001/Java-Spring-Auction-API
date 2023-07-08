package com.hcmus.auction.common.variable;

public enum SuccessMessage {
    ADD_FAVORITE_PRODUCT_SUCCESSFULLY("Add favorite product successfully!"),
    DELETE_FAVORITE_PRODUCT_SUCCESSFULLY("Delete favorite product successfully!"),
    ADD_NEW_ROLE_HISTORY_SUCCESSFULLY("Send upgrade role request successfully!"),
    ADD_NEW_PRODUCT_DESCRIPTION_SUCCESSFULLY("Add new product description successfully!"),
    ADD_NEW_PRODUCT_SUCCESSFULLY("Add new product successfully"),
    UPDATE_PROFILE_SUCCESSFULLY("Update profile successfully!");

    SuccessMessage(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return this.message;
    }
}
