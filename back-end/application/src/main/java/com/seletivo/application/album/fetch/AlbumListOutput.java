package com.seletivo.application.album.fetch;

import com.seletivo.domain.album.Album;
import java.util.UUID;

public record AlbumListOutput(
        UUID secureId,
        String titulo,
        UUID artistaId
) {
    public static AlbumListOutput from(final Album anAlbum, final UUID artistaSecureId) {
        return new AlbumListOutput(
                anAlbum.getSecureId(),
                anAlbum.getTitulo(),
                artistaSecureId
        );
    }
}