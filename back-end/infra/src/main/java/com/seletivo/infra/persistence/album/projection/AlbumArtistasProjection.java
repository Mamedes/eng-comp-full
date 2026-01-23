package com.seletivo.infra.persistence.album.projection;

import java.util.UUID;

public interface AlbumArtistasProjection {
    UUID getAlbumSecureId();
    String getAlbumTitulo();
    String getArtistaNome();
    String getArtistaTipo();
}