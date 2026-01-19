package com.seletivo.application.album.albumImage.create;

import com.seletivo.domain.album.AlbumImagem;

import java.util.UUID;

public record CreateAlbumImagemOutput(UUID id) {

    public static CreateAlbumImagemOutput from(final UUID anId) {
        return new CreateAlbumImagemOutput(anId);
    }

    public static CreateAlbumImagemOutput from(final AlbumImagem anAlbumImagem) {
        return new CreateAlbumImagemOutput(anAlbumImagem.getSecureId());
    }
}
