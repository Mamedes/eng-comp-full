package com.seletivo.infra.api.controller.artista.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seletivo.infra.api.controller.albumImage.response.AlbumImagemResponse;
import java.util.List;
import java.util.UUID;

public record AlbumDetalheResponse(
        @JsonProperty("albumId") UUID albumId,
        @JsonProperty("titulo") String titulo,
        @JsonProperty("imagens") List<AlbumImagemResponse> imagens
) {}