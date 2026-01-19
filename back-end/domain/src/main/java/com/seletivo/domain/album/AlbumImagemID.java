package com.seletivo.domain.album;

import com.seletivo.domain.Identifier;

import java.util.Objects;

public final class AlbumImagemID extends Identifier {
    private final Long value;
    private final String secureId;

    private AlbumImagemID(final Long value, final String secureId) {
        this.value = Objects.requireNonNull(value);
        this.secureId = Objects.requireNonNull(secureId);
    }

    public static AlbumImagemID from(final Long anId, final String aSecureId) {
        return new AlbumImagemID(anId, aSecureId);
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
        AlbumImagemID that = (AlbumImagemID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
