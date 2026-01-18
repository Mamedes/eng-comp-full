package com.seletivo.application.artista.fetch.list;

import com.seletivo.domain.artista.Artista;
import java.util.UUID;

public record ArtistaListOutput(
        Long id,
        UUID secureId,
        String nome,
        String tipo
) {
    public static ArtistaListOutput from(final Artista aArtista) {
        return new ArtistaListOutput(
                aArtista.getId().getValue(),
                aArtista.getSecureId(),
                aArtista.getNome(),
                aArtista.getTipo()
        );
    }
}
