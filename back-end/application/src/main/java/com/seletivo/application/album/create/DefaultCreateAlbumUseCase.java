package com.seletivo.application.album.create;


import com.seletivo.application.album.notification.AlbumNotificationService;
import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.validation.Error;
import com.seletivo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.HashSet;
import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateAlbumUseCase extends CreateAlbumUseCase {

    private final AlbumGateway albumGateway;
    private final ArtistaGateway artistaGateway;
    private final AlbumNotificationService notificationService;

    public DefaultCreateAlbumUseCase(final AlbumGateway albumGateway, final ArtistaGateway artistaGateway, AlbumNotificationService notificationService) {
        this.albumGateway = Objects.requireNonNull(albumGateway);
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
        this.notificationService = Objects.requireNonNull(notificationService);
    }

    @Override
    public Either<Notification, CreateAlbumOutput> execute(final CreateAlbumCommand aCommand) {
        final var notification = Notification.create();
        final var artistasIdsComand = aCommand.artistasIds();

        final var artistasFind = this.artistaGateway.existsBySecureIds(artistasIdsComand);

        if (artistasFind.size() != artistasIdsComand.size()) {
            notification.append(new Error("Um ou mais artistas não foram encontrados"));
            return Left(notification);
        }
        final var anAlbum = Album.newAlbum(aCommand.titulo(), new HashSet<>(artistasFind));
        anAlbum.validate(notification);

        return notification.hasError() ? Left(notification) : create(anAlbum);
    }

    private Either<Notification, CreateAlbumOutput> create(final Album anAlbum) {
        return Try(() -> this.albumGateway.create(anAlbum))
                .toEither()
                .bimap(Notification::create,CreateAlbumOutput::from)
                .peek(output -> notificationService.notifyAlbumCreated("Novo álbum: " + anAlbum.getTitulo()));
    }
}
