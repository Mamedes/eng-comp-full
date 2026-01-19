package com.seletivo.application.album.update;

import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.artista.ArtistaID;
import com.seletivo.domain.exceptions.NotFoundException;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

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

        final var anAlbum = this.albumGateway.findBySecureId(aSecureId)
                .orElseThrow(notFound(aSecureId));

        ArtistaID artistaId = null;
        if (aCommand.artistaId() != null) {
            artistaId = artistaGateway.findBySecureId(aCommand.artistaId())
                    .map(Artista::getId)
                    .orElse(null);

        }

        final var notification = Notification.create();

        final var albumAtualizado = anAlbum.update(aCommand.titulo(), artistaId.getValue());

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