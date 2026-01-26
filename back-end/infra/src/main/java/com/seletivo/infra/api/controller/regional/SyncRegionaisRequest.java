package com.seletivo.infra.api.controller.regional;



import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record SyncRegionaisRequest(
        @JsonProperty("regionais") List<RegionalItemRequest> regionais
) {}