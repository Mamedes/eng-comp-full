package com.seletivo.application.album.update;

import com.seletivo.domain.album.Album;
import java.util.UUID;

public record UpdateAlbumOutput(UUID secureId) {
    public static UpdateAlbumOutput from(final Album anAlbum) {
        return new UpdateAlbumOutput(anAlbum.getSecureId());
    }
}
