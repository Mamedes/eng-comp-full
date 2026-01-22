package com.seletivo.application.album.fetch.projection;

import java.util.UUID;

public record ArtistaListViewOutput(
        UUID artistaId,
        String nome,
        String tipo,
        Integer quantidadeAlbuns
) {
}