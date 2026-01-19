package com.seletivo.application.album.create;

import com.seletivo.domain.album.Album;
import java.util.UUID;

public record CreateAlbumOutput(
        UUID id
) {
    public static CreateAlbumOutput from(final UUID anId) {
        return new CreateAlbumOutput(anId);
    }

    public static CreateAlbumOutput from(final Album anAlbum) {
        return new CreateAlbumOutput(anAlbum.getSecureId());
    }
}
