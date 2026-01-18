package com.seletivo.infra.api.controller.login;


import com.seletivo.infra.api.controller.login.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "auth")
@Tag(name = "1. Autenticação", description = "Gerenciamento de token")
public interface



AutenticacaoAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "login"
    )
    @Operation(summary = "Serviço para gerar token e o refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successo"),
            @ApiResponse(responseCode = "422", description = "Erro de validação encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro 500"),
    })
    ResponseEntity<?> login(@RequestBody LoginRequest input);

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "refresh-token"
    )
    @Operation(summary = "Serviço para refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successo"),
            @ApiResponse(responseCode = "422", description = "Erro de validação encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro 500"),
    })
    ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authorizationHeader,  @RequestBody(required = false) Object body);
}
