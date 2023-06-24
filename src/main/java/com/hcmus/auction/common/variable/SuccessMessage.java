package com.hcmus.auction.common.variable;

public enum SuccessMessage {
    ADD_FAVORITE_PRODUCT_SUCCESSFULLY("Add favorite product successfully!"),
    DELETE_FAVORITE_PRODUCT_SUCCESSFULLY("Delete favorite product successfully!");

    SuccessMessage(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return this.message;
    }
}
