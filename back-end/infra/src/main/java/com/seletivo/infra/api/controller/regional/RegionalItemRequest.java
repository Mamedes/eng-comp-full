package com.seletivo.infra.api.controller.regional;

import com.fasterxml.jackson.annotation.JsonProperty;

record RegionalItemRequest(
        @JsonProperty("id") Long id,
        @JsonProperty("nome") String nome
) {}