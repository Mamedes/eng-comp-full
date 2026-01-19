package com.seletivo.application.album.create;

import java.util.UUID;

public record CreateAlbumCommand(
        String titulo,
        UUID artistaID
) {
    public static CreateAlbumCommand with(final String titulo, final UUID artistaID) {
        return new CreateAlbumCommand(titulo, artistaID);
    }
}
