package com.seletivo.application.artista.create;


import com.seletivo.domain.artista.Artista;

import java.util.UUID;

public record CreateArtistaOutput(
        UUID artistaId
) {
    public static CreateArtistaOutput from(final UUID anId) {
        return new CreateArtistaOutput(anId);
    }

    public static CreateArtistaOutput from(final Artista anArtista) {
        return new CreateArtistaOutput(anArtista.getSecureId());
    }
}
