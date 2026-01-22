package com.seletivo.infra.persistence.artista.projection;

import java.util.UUID;

public interface ArtistaListViewProjection {
    UUID getSecureId();
    String getNome();
    String getTipo();
    Integer getQuantidadeAlbuns();
}