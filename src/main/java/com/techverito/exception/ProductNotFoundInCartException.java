package com.techverito.exception;

public class ProductNotFoundInCartException extends RuntimeException {

    private static final String PRODUCT_NOT_FOUND = "Product not found in cart";

    public ProductNotFoundInCartException() {
        super(PRODUCT_NOT_FOUND);
    }

}
