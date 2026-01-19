package com.seletivo.application.album.albumImage.create;

import com.seletivo.domain.album.AlbumImagem;

public record CreateAlbumImagemOutput(Long id) {

    public static CreateAlbumImagemOutput from(final Long anId) {
        return new CreateAlbumImagemOutput(anId);
    }

    public static CreateAlbumImagemOutput from(final AlbumImagem anAlbumImagem) {
        return new CreateAlbumImagemOutput(anAlbumImagem.getId().getValue());
    }
}
