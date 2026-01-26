package com.seletivo.infra.api.controller.regional;

import com.seletivo.application.regional.sync.RegionalInput;
import com.seletivo.application.regional.sync.SyncRegionaisCommand;
import com.seletivo.application.regional.sync.SyncRegionaisOutput;
import com.seletivo.application.regional.sync.SyncRegionaisUseCase;
import com.seletivo.domain.validation.handler.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.function.Function;

@RestController
public class RegionalController implements RegionalAPI {

    private final SyncRegionaisUseCase syncRegionaisUseCase;

    public RegionalController(
            final SyncRegionaisUseCase syncRegionaisUseCase

    ) {
        this.syncRegionaisUseCase = Objects.requireNonNull(syncRegionaisUseCase);
    }
    @Override
    public ResponseEntity<?> syncRegionais(final SyncRegionaisRequest input) {
        final var aCommand = SyncRegionaisCommand.with(
                input.regionais().stream()
                        .map(item -> RegionalInput.with(item.id(), item.nome()))
                        .toList()
        );

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<SyncRegionaisOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return this.syncRegionaisUseCase.execute(aCommand).fold(onError, onSuccess);
    }

}