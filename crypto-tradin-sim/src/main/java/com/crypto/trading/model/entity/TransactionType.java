package com.crypto.trading.model.entity;

public enum TransactionType {
    BUY("BUY"), SELL("SELL");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static TransactionType fromValue(String value) {
        for (TransactionType t : TransactionType.values()) {
            if (t.value.equals(value.toUpperCase())) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid transaction type: " + value);
    }
}
