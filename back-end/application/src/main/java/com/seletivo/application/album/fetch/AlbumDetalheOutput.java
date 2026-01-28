package com.seletivo.application.album.fetch;


import com.seletivo.application.album.albumImage.AlbumImagemOutput;
import com.seletivo.domain.album.Album;
import java.util.List;
import java.util.UUID;

public record AlbumDetalheOutput(
        UUID id,
        String titulo,
        List<AlbumImagemOutput> imagens
) {
    public static AlbumDetalheOutput from(final Album album, final List<AlbumImagemOutput> imagens) {
        return new AlbumDetalheOutput(
                album.getSecureId(),
                album.getTitulo(),
                imagens
        );
    }
}