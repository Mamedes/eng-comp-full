package com.seletivo.application.artista.create;


import com.seletivo.domain.artista.Artista;

public record CreateArtistaOutput(
        Long id
) {
    public static CreateArtistaOutput from(final Long anId) {
        return new CreateArtistaOutput(anId);
    }

    public static CreateArtistaOutput from(final Artista anArtista) {
        return new CreateArtistaOutput(anArtista.getId().getValue());
    }
}
