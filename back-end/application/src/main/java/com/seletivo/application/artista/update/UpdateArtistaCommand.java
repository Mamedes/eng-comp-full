package com.seletivo.application.artista.update;

import java.util.UUID;

public record UpdateArtistaCommand(
        UUID secureId,
        String nome,
        String tipo
) {
    public static UpdateArtistaCommand with(final UUID aSecureId, final String nome, final String tipo) {
        return new UpdateArtistaCommand(aSecureId, nome, tipo);
    }
}
