package com.seletivo.application.album.fetch;

import com.seletivo.domain.album.Album;
import java.time.Instant;
import java.util.UUID;

public record AlbumListOutput(
        UUID secureId,
        String titulo,
        UUID artistaId,
        Instant createdAt
) {
    public static AlbumListOutput from(final Album anAlbum) {
        return new AlbumListOutput(
                anAlbum.getSecureId(),
                anAlbum.getTitulo(),
                anAlbum.getArtistaID().getSecureId() != null
                        ? UUID.fromString(anAlbum.getArtistaID().getSecureId())
                        : null,
                anAlbum.getCreatedAt()
        );
    }
}