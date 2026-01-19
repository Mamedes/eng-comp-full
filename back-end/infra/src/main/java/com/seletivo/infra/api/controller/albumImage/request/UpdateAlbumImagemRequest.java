package com.seletivo.infra.api.controller.albumImage.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UpdateAlbumImagemRequest(
        @JsonProperty("albumId") Long albumId,
        @JsonProperty("files") List<MultipartFile> files
) {
}