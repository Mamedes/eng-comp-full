package com.seletivo.infra.api.controller.album.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.UUID;

public record CreateAlbumRequest(
        @JsonProperty("albumTitulo") String titulo,
        @JsonProperty("artistaIds") Set<UUID> artistasIds
) {
}