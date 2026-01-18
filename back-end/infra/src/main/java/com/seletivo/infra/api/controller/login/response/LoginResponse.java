package com.seletivo.infra.api.controller.login.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(
        @JsonProperty("accessToken") String accessToken,
        @JsonProperty("refreshToken") String refreshToken
) {
}
