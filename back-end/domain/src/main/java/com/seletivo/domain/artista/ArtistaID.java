package com.seletivo.domain.artista;

import com.seletivo.domain.Identifier;
import java.util.Objects;

public final class ArtistaID extends Identifier {
    private final Long value;
    private final String secureId;

    private ArtistaID(final Long value, final String secureId) {
        this.value = Objects.requireNonNull(value);
        this.secureId = Objects.requireNonNull(secureId);
    }

    public static ArtistaID from(final Long anId, final String aSecureId) {
        return new ArtistaID(anId, aSecureId);
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
        ArtistaID that = (ArtistaID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
