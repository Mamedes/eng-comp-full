package com.seletivo.application.artista.update;

import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.exceptions.NotFoundException;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateArtistaUseCase extends UpdateArtistaUseCase {

    private final ArtistaGateway artistaGateway;

    public DefaultUpdateArtistaUseCase(final ArtistaGateway artistaGateway) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public Either<Notification, UpdateArtistaOutput> execute(final UpdateArtistaCommand aCommand) {
        final var aSecureId = aCommand.secureId();

        final var aArtista = this.artistaGateway.findBySecureId(aSecureId)
                .orElseThrow(notFound(aSecureId));
        final var notification = Notification.create();
        final var artistaAtualizado = aArtista.update(aCommand.nome(), aCommand.tipo());
        artistaAtualizado.validate(notification);

        return notification.hasError() ? Left(notification) : update(artistaAtualizado);
    }
    private Either<Notification, UpdateArtistaOutput> update(final Artista aArtista) {
        return Try(() -> this.artistaGateway.update(aArtista))
                .toEither()
                .bimap(Notification::create, UpdateArtistaOutput::from);
    }

    private Supplier<NotFoundException> notFound(final UUID aSecureId) {
        return () -> NotFoundException.with(Artista.class, aSecureId.toString());
    }
}