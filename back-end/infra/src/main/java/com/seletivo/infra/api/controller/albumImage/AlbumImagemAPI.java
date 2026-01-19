package com.seletivo.infra.api.controller.albumImage;

import com.seletivo.infra.api.controller.albumImage.request.CreateAlbumImagemRequest;
import com.seletivo.infra.api.controller.albumImage.request.UpdateAlbumImagemRequest;
import com.seletivo.infra.api.controller.albumImage.response.AlbumImagemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "album-imagem")
@Tag(name = "Album Imagem")
public interface AlbumImagemAPI {

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new album imagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createImagem(@ModelAttribute CreateAlbumImagemRequest input);

    @GetMapping(value = "/album/{id}")
    @Operation(summary = "List all images from a specific album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    List<AlbumImagemResponse> listByAlbum(@PathVariable(name = "id") UUID album_id);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get an album imagem by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album Imagem retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Album Imagem was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    AlbumImagemResponse getById(@PathVariable(name = "id") UUID id);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update an album imagem by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album Imagem updated successfully"),
            @ApiResponse(responseCode = "404", description = "Album Imagem was not found"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> updateAlbumImagem(@PathVariable(name = "id") Long id, @ModelAttribute UpdateAlbumImagemRequest input);

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Delete an album imagem by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Album Imagem deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Album Imagem was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable(name = "id") Long id);
}
