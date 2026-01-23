package com.seletivo.application.album.fetch;

import com.seletivo.domain.album.Album;

import java.util.Set;
import java.util.UUID;

public record AlbumListOutput(
        UUID secureId,
        String titulo,
        Set<UUID> artistasIds
) {
    public static AlbumListOutput from(final Album anAlbum, final Set<UUID> artistasIds) {
        return new AlbumListOutput(
                anAlbum.getSecureId(),
                anAlbum.getTitulo(),
                artistasIds
        );
    }
}