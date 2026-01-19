package com.seletivo.application.album.fetch;

import com.seletivo.domain.album.Album;
import java.time.Instant;
import java.util.UUID;

public record AlbumOutput(
        UUID secureId,
        String titulo,
        UUID artistaId,
        Instant createdAt,
        Instant updatedAt
) {
    public static AlbumOutput from(final Album anAlbum) {
        return new AlbumOutput(
                anAlbum.getSecureId(),
                anAlbum.getTitulo(),
                anAlbum.getArtistaID() != null && anAlbum.getArtistaID().getSecureId() != null
                        ? UUID.fromString(anAlbum.getArtistaID().getSecureId())
                        : null,
                anAlbum.getCreatedAt(),
                anAlbum.getUpdatedAt()
        );
    }
}
