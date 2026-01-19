package com.seletivo.application.album.create;


import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.validation.Error;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateAlbumUseCase extends CreateAlbumUseCase {

    private final AlbumGateway albumGateway;
    private final ArtistaGateway artistaGateway;

    public DefaultCreateAlbumUseCase(final AlbumGateway albumGateway, final ArtistaGateway artistaGateway) {
        this.albumGateway = Objects.requireNonNull(albumGateway);
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public Either<Notification, CreateAlbumOutput> execute(final CreateAlbumCommand aCommand) {
        final var notification = Notification.create();

        final var oArtista = this.artistaGateway.findBySecureId(aCommand.artistaID());

        if (oArtista.isEmpty()) {
            notification.append(new Error("Artista com ID %s não foi encontrado".formatted(aCommand.artistaID())));
            return Left(notification);
        }

        final var anArtista = oArtista.get();
        final var anAlbum = Album.newAlbum(aCommand.titulo(), anArtista.getId());

        anAlbum.validate(notification);

        return notification.hasError() ? Left(notification) : create(anAlbum);
    }

    private Either<Notification, CreateAlbumOutput> create(final Album anAlbum) {
        return Try(() -> this.albumGateway.create(anAlbum))
                .toEither()
                .bimap(Notification::create,CreateAlbumOutput::from);
    }
}
