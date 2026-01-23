package com.seletivo.application.album.fetch;


import java.util.UUID;

public record AlbumArtistaListView(
        UUID albumId,
        String albumTitulo,
        String artistaNome,
        String artistaTipo
) {}
