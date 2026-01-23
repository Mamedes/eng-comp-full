package com.seletivo.infra.api.controller.album.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.UUID;

public record UpdateAlbumRequest(
        @JsonProperty("titulo") String titulo,
        @JsonProperty("artistas_ids") Set<UUID> artistasIds
) {
}