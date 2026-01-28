package com.seletivo.application.album.albumImage.fetch;

import com.seletivo.domain.album.AlbumImagem;

public record AlbumImagemDetalheOutput(
        String fileName,
        String bucket,
        String contentType
) {
    public static AlbumImagemDetalheOutput from(final AlbumImagem imagem) {
        return new AlbumImagemDetalheOutput(
                imagem.getFileName(),
                imagem.getFileKey(),
                imagem.getContentType()
        );
    }
}