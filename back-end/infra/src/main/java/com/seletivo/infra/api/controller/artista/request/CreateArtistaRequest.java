package com.seletivo.infra.api.controller.artista.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateArtistaRequest(
        @JsonProperty("nome") String nome,
        @JsonProperty("tipo") String tipo
) {
}