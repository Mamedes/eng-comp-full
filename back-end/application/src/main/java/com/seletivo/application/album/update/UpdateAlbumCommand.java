package com.seletivo.application.album.update;

import java.util.Set;
import java.util.UUID;

public record UpdateAlbumCommand(
        UUID secureId,
        String titulo,
        Set<UUID> artistasIDs
) {
    public static UpdateAlbumCommand with(final UUID aSecureId, final String titulo, final Set<UUID> artistasIDs) {
        return new UpdateAlbumCommand(aSecureId, titulo, artistasIDs);
    }
}