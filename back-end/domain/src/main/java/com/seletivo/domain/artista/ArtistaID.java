package com.seletivo.domain.artista;

import com.seletivo.domain.Identifier;
import java.util.Objects;

public final class ArtistaID extends Identifier {
    private final Long value;

    private ArtistaID(final Long value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ArtistaID from(final Long anId) {
        return new ArtistaID(anId);
    }

    public Long getValue() {
        return value;
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
