package com.seletivo.infra.api.controller.artista.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UpdateArtistaRequest(
        @JsonProperty("artistaId")UUID artistaId,
        @JsonProperty("nome") String nome,
        @JsonProperty("tipo") String tipo
){
}
