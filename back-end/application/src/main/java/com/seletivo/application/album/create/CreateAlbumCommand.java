package com.seletivo.application.album.create;

import java.util.Set;
import java.util.UUID;

public record CreateAlbumCommand(
        String titulo,
        Set<UUID> artistasIds
) {
    public static CreateAlbumCommand with(final String titulo, final Set<UUID> artistasIds) {
        return new CreateAlbumCommand(titulo, artistasIds);
    }
}
