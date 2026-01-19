package com.seletivo.infra.api.controller.album;

import com.seletivo.application.album.create.CreateAlbumCommand;
import com.seletivo.application.album.create.CreateAlbumOutput;
import com.seletivo.application.album.create.CreateAlbumUseCase;
import com.seletivo.domain.validation.handler.Notification;
import com.seletivo.infra.api.controller.album.request.CreateAlbumRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class AlbumController implements AlbumAPI {

    private final CreateAlbumUseCase createAlbumUseCase;

    public AlbumController(final CreateAlbumUseCase createAlbumUseCase) {
        this.createAlbumUseCase = Objects.requireNonNull(createAlbumUseCase);
    }

    @Override
    public ResponseEntity<?> createAlbum(final CreateAlbumRequest input) {
        final var aCommand = CreateAlbumCommand.with(
                input.titulo(),
                input.artistaId()
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateAlbumOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/album/" + output.id())).body(output);

        return this.createAlbumUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }
}
