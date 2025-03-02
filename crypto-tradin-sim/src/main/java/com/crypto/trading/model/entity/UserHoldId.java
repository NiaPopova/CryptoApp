package com.crypto.trading.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
public class UserHoldId implements Serializable {
    private Integer userId;
    private Integer cryptoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHoldId that = (UserHoldId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(cryptoId, that.cryptoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, cryptoId);
    }
}
