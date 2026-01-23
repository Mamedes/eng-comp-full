package com.seletivo.application.album.fetch;

import com.seletivo.domain.album.Album;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record AlbumOutput(
        UUID secureId,
        String titulo,
        Set<UUID> artistasIds,
        Instant createdAt,
        Instant updatedAt
) {
    public static AlbumOutput from(final Album anAlbum, Set<UUID> artistasIds) {
        return new AlbumOutput(
                anAlbum.getSecureId(),
                anAlbum.getTitulo(),
                artistasIds,
                anAlbum.getCreatedAt(),
                anAlbum.getUpdatedAt()
        );
    }
}