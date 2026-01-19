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
    public static AlbumOutput from(final Album anAlbum,  UUID aArtistaId) {
        return new AlbumOutput(
                anAlbum.getSecureId(),
                anAlbum.getTitulo(),
                aArtistaId,
                anAlbum.getCreatedAt(),
                anAlbum.getUpdatedAt()
        );
    }
}
