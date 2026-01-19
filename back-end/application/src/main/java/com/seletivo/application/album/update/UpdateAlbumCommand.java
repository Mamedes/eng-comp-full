package com.seletivo.application.album.update;

import java.util.UUID;

public record UpdateAlbumCommand(
        UUID secureId,
        String titulo,
        UUID artistaId
) {
    public static UpdateAlbumCommand with(final UUID aSecureId, final String titulo, final UUID artistaId) {
        return new UpdateAlbumCommand(aSecureId, titulo, artistaId);
    }
}