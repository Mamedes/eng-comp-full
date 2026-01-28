package com.seletivo.application.artista.fetch.details;

import com.seletivo.application.album.fetch.AlbumDetalheOutput;
import com.seletivo.domain.artista.Artista;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ArtistaDetalheOutput(
        UUID id,
        String nome,
        String tipo,
        Instant createdAt,
        Instant updatedAt,
        List<AlbumDetalheOutput> albuns
) {
    public static ArtistaDetalheOutput from(final Artista artista, final List<AlbumDetalheOutput> albuns) {
        return new ArtistaDetalheOutput(
                artista.getSecureId(),
                artista.getNome(),
                artista.getTipo(),
                artista.getCreatedAt(),
                artista.getUpdatedAt(),
                albuns
        );
    }
}