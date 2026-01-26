package com.seletivo.infra.api.controller.regional;

import com.seletivo.domain.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "v1/regionais")
public interface RegionalAPI {

    @PostMapping(
            value = "/sync",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Sincroniza regionais com a API externa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Synchronized successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> syncRegionais(@RequestBody SyncRegionaisRequest input);


}