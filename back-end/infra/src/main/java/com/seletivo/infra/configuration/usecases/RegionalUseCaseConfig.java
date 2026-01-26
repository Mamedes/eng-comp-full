package com.seletivo.infra.configuration.usecases;

import com.seletivo.application.regional.sync.DefaultSyncRegionaisUseCase;
import com.seletivo.application.regional.sync.SyncRegionaisUseCase;
import com.seletivo.domain.regional.RegionalGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class RegionalUseCaseConfig {

    private final RegionalGateway regionalGateway;

    public RegionalUseCaseConfig(final RegionalGateway regionalGateway) {
        this.regionalGateway = Objects.requireNonNull(regionalGateway);
    }

    @Bean
    public SyncRegionaisUseCase syncRegionaisUseCase() {
        return new DefaultSyncRegionaisUseCase(regionalGateway);
    }

}