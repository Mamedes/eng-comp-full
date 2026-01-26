package com.seletivo.domain.regional;

import com.seletivo.domain.Identifier;
import java.util.Objects;

public final class RegionalID extends Identifier {
    private final Long value;
    private final String secureId;

    private RegionalID(final Long value, final String secureId) {
        this.value = Objects.requireNonNull(value);
        this.secureId = secureId;
    }

    public static RegionalID from(final Long value, final String aSecureId) {
        return new RegionalID(value, aSecureId);
    }
    public Long getValue() {
        return value;
    }

    @Override
    public String getSecureId() {
        return secureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionalID that = (RegionalID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}