package com.techverito.exception;

public class BalanceInsufficientException extends RuntimeException{

    private static final String BALANCE_INSUFFICIENT = "Insufficient balance in wallet";

    public BalanceInsufficientException() {
        super(BALANCE_INSUFFICIENT);
    }
}
