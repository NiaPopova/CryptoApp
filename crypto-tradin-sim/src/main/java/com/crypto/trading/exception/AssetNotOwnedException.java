package com.crypto.trading.exception;

public class AssetNotOwnedException extends RuntimeException {
    public AssetNotOwnedException(String message) {
        super(message);
    }
}
