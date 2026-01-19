package com.seletivo.infra.api.controller.album;


import com.seletivo.domain.pagination.Pagination;
import com.seletivo.infra.api.controller.album.request.CreateAlbumRequest;
import com.seletivo.infra.api.controller.album.request.UpdateAlbumRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "album")
public interface AlbumAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new album")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> createAlbum(@RequestBody CreateAlbumRequest input);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get an album by its identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Album was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    AlbumResponse getById(@PathVariable(name = "id") UUID id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all albums paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<AlbumListResponse> listAlbums(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "titulo") String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") String direction
    );

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an album by its identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album updated successfully"),
            @ApiResponse(responseCode = "404", description = "Album was not found"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> updateById(@PathVariable(name = "id") UUID id, @RequestBody UpdateAlbumRequest input);

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an album by its identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Album deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Album was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    void deleteById(@PathVariable(name = "id") UUID id);
}
