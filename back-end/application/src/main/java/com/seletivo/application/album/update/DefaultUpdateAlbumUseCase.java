package com.seletivo.application.album.update;

import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.exceptions.NotFoundException;
import com.seletivo.domain.validation.Error;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateAlbumUseCase extends UpdateAlbumUseCase {

    private final AlbumGateway albumGateway;
    private final ArtistaGateway artistaGateway;

    public DefaultUpdateAlbumUseCase(final AlbumGateway albumGateway, final ArtistaGateway artistaGateway) {
        this.albumGateway = Objects.requireNonNull(albumGateway);
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public Either<Notification, UpdateAlbumOutput> execute(final UpdateAlbumCommand aCommand) {
        final var aSecureId = aCommand.secureId();
        final var artistasIDs = aCommand.artistasIDs();
        final var notification = Notification.create();


        final var anAlbum = this.albumGateway.findBySecureId(aSecureId)
                .orElseThrow(notFound(aSecureId));

        final var artistasFind = this.artistaGateway.existsBySecureIds(artistasIDs);

        if (artistasFind.size() != artistasIDs.size()) {
            notification.append(new Error("Um ou mais artistas não foram encontrados."));
            return Left(notification);
        }

        final var albumAtualizado = anAlbum.update(
                aCommand.titulo(),
                new HashSet<>(artistasFind)
        );
        albumAtualizado.validate(notification);

        return notification.hasError() ? Left(notification) : update(albumAtualizado);
    }

    private Either<Notification, UpdateAlbumOutput> update(final Album anAlbum) {
        return Try(() -> this.albumGateway.update(anAlbum))
                .toEither()
                .bimap(Notification::create, UpdateAlbumOutput::from);
    }

    private Supplier<NotFoundException> notFound(final UUID aSecureId) {
        return () -> NotFoundException.with(Album.class, aSecureId);
    }
}