package com.seletivo.application.artista.update;

import com.seletivo.domain.artista.Artista;
import java.util.UUID;

public record UpdateArtistaOutput(UUID secureId) {
    public static UpdateArtistaOutput from(final Artista aArtista) {
        return new UpdateArtistaOutput(aArtista.getSecureId());
    }
}