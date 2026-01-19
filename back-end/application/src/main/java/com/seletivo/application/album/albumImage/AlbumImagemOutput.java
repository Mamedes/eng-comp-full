package com.seletivo.application.album.albumImage;

import com.seletivo.domain.album.AlbumImagem;
import com.seletivo.domain.album.AlbumImagemID;

import java.time.Instant;
import java.util.UUID;

public record AlbumImagemOutput(
        AlbumImagemID id,
        UUID secureId,
        Long albumId,
        String fileName,
        String bucket,
        String contentType,
        Instant createdAt
) {

    public static AlbumImagemOutput from(final AlbumImagem anAlbumImagem) {
        return new AlbumImagemOutput(
                anAlbumImagem.getId(),
                anAlbumImagem.getSecureId(),
                anAlbumImagem.getAlbumId(),
                anAlbumImagem.getFileName(),
                anAlbumImagem.getFileKey(),
                anAlbumImagem.getContentType(),
                anAlbumImagem.getCreatedAt()
        );
    }
}
