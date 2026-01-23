package com.seletivo.domain.album;

import com.seletivo.domain.artista.ArtistaID;

import java.util.Objects;

public class AlbumArtista {
    private final AlbumID albumId;
    private final ArtistaID artistaId;

    private AlbumArtista(final AlbumID albumId, final ArtistaID artistaId) {
        this.albumId = Objects.requireNonNull(albumId);
        this.artistaId = Objects.requireNonNull(artistaId);
    }

    public static AlbumArtista with(final AlbumID albumId, final ArtistaID artistaId) {
        return new AlbumArtista(albumId, artistaId);
    }

    public AlbumID getAlbumId() { return albumId; }
    public ArtistaID getArtistaId() { return artistaId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumArtista that = (AlbumArtista) o;
        return Objects.equals(albumId, that.albumId) && Objects.equals(artistaId, that.artistaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, artistaId);
    }
}