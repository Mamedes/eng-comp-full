package com.seletivo.application.artista.create;

import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateArtistaUseCase extends CreateArtistaUseCase {

    private final ArtistaGateway artistaGateway;

    public DefaultCreateArtistaUseCase(final ArtistaGateway artistaGateway) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public Either<Notification, CreateArtistaOutput> execute(final CreateArtistaCommand aCommand) {
        final var nome = aCommand.nome();
        final var tipo = aCommand.tipo();

        final var notification = Notification.create();

        final var anArtista = Artista.newArtista(nome, tipo);
        anArtista.validate(notification);

        return notification.hasError() ? Left(notification) : create(anArtista);
    }

    private Either<Notification, CreateArtistaOutput> create(final Artista anArtista) {
        return Try(() -> this.artistaGateway.create(anArtista))
                .toEither()
                .bimap(Notification::create, CreateArtistaOutput::from);
    }
}
