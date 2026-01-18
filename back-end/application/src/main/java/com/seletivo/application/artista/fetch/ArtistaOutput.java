package com.seletivo.application.artista.fetch;

import com.seletivo.domain.artista.Artista;
import java.util.UUID;

public record ArtistaOutput(
        Long id,
        UUID secureId,
        String nome,
        String tipo
) {
    public static ArtistaOutput from(final Artista a) {
        return new ArtistaOutput(
                a.getId().getValue(),
                a.getSecureId(),
                a.getNome(),
                a.getTipo()
        );
    }
}