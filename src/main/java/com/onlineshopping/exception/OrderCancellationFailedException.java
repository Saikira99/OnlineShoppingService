package com.onlineshopping.exception;

public class OrderCancellationFailedException extends RuntimeException {
    public OrderCancellationFailedException(String message) {
        super(message);
    }
}
