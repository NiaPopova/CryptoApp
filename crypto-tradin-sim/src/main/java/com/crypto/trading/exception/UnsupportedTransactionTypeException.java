package com.crypto.trading.exception;

public class UnsupportedTransactionTypeException extends RuntimeException {
    public UnsupportedTransactionTypeException(String message) {
        super(message);
    }
}
