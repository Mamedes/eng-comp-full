package com.seletivo.application.regional.sync;

import com.seletivo.domain.regional.Regional;
import com.seletivo.domain.regional.RegionalGateway;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.vavr.API.Try;

public class DefaultSyncRegionaisUseCase extends SyncRegionaisUseCase {

    private final RegionalGateway regionalGateway;

    public DefaultSyncRegionaisUseCase(final RegionalGateway regionalGateway) {
        this.regionalGateway = Objects.requireNonNull(regionalGateway);
    }

    @Override
    public Either<Notification, SyncRegionaisOutput> execute(final SyncRegionaisCommand aCommand) {

        return Try(() -> {
            final var regionalAllActive = this.regionalGateway.findAllActive();
            final var regionalMap = regionalAllActive.stream()
                    .collect(Collectors.toMap(Regional::getExternalId, r -> r));

            final var toSaveRegional = new ArrayList<Regional>();
            final var apiExternalIds = aCommand.regionais().stream()
                    .map(RegionalInput::id)
                    .collect(Collectors.toSet());

            for (final var apiDto : aCommand.regionais()) {
                final var local = regionalMap.get(apiDto.id());

                if (local == null) {
                    toSaveRegional.add(Regional.newRegional(apiDto.id(), apiDto.nome()));
                } else if (!local.getNome().equalsIgnoreCase(apiDto.nome())) {
                    toSaveRegional.add(local.inativar());
                    toSaveRegional.add(Regional.newRegional(apiDto.id(), apiDto.nome()));
                }
            }

            regionalAllActive.stream()
                    .filter(l -> !apiExternalIds.contains(l.getExternalId()))
                    .forEach(l -> toSaveRegional.add(l.inativar()));

            if (!toSaveRegional.isEmpty()) {
                this.regionalGateway.saveAll(toSaveRegional);
            }

            return SyncRegionaisOutput.from(true, toSaveRegional.size());
        })
                .toEither()
                .mapLeft(Notification::create);
    }
}