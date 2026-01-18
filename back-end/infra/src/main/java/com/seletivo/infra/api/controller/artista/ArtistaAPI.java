package com.seletivo.infra.api.controller.artista;

import com.seletivo.domain.pagination.Pagination;
import com.seletivo.infra.api.controller.artista.request.CreateArtistaRequest;
import com.seletivo.infra.api.controller.artista.request.UpdateArtistaRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "artista")
public interface ArtistaAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createArtista(@RequestBody CreateArtistaRequest input);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get an artista by its identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artista retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Artista was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ArtistaResponse getById(@PathVariable(name = "id") UUID id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all artistas paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "An invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<ArtistaListResponse> listArtistas(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "nome") String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") String direction
    );

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a unidade by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Unidade updated successfully"),
            @ApiResponse(responseCode = "404", description = "Unidade was not found"),
            @ApiResponse(responseCode = "500",
                    description = "An internal server error was thrown"),})
    ResponseEntity<?> updateById(@PathVariable(name = "id") UUID id,
                                 @RequestBody UpdateArtistaRequest input);

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Artista by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Artista deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Artista was not found"),
            @ApiResponse(responseCode = "500",
                    description = "An internal server error was thrown"),})
    void deleteById(@PathVariable(name = "id") UUID id);


}