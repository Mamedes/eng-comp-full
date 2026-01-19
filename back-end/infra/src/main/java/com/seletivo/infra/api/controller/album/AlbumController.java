package com.seletivo.infra.api.controller.album;

import com.seletivo.application.album.create.CreateAlbumCommand;
import com.seletivo.application.album.create.CreateAlbumOutput;
import com.seletivo.application.album.create.CreateAlbumUseCase;
import com.seletivo.application.album.delete.DeleteAlbumUseCase;
import com.seletivo.application.album.fetch.get.GetAlbumByIdUseCase;
import com.seletivo.application.album.fetch.list.ListAlbumUseCase;
import com.seletivo.application.album.update.UpdateAlbumCommand;
import com.seletivo.application.album.update.UpdateAlbumOutput;
import com.seletivo.application.album.update.UpdateAlbumUseCase;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import com.seletivo.domain.validation.handler.Notification;
import com.seletivo.infra.api.controller.album.presenter.AlbumApiPresenter;
import com.seletivo.infra.api.controller.album.request.CreateAlbumRequest;
import com.seletivo.infra.api.controller.album.request.UpdateAlbumRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@RestController
public class AlbumController implements AlbumAPI {

    private final CreateAlbumUseCase createAlbumUseCase;
    private final GetAlbumByIdUseCase getAlbumByIdUseCase;
    private final ListAlbumUseCase listAlbumUseCase;
    private final UpdateAlbumUseCase updateAlbumUseCase;
    private final DeleteAlbumUseCase deleteAlbumUseCase;

    public AlbumController(
            final CreateAlbumUseCase createAlbumUseCase,
            final GetAlbumByIdUseCase getAlbumByIdUseCase,
            final ListAlbumUseCase listAlbumUseCase,
            final UpdateAlbumUseCase updateAlbumUseCase,
            final DeleteAlbumUseCase deleteAlbumUseCase
    ) {
        this.createAlbumUseCase = Objects.requireNonNull(createAlbumUseCase);
        this.getAlbumByIdUseCase = Objects.requireNonNull(getAlbumByIdUseCase);
        this.listAlbumUseCase = Objects.requireNonNull(listAlbumUseCase);
        this.updateAlbumUseCase = Objects.requireNonNull(updateAlbumUseCase);
        this.deleteAlbumUseCase = Objects.requireNonNull(deleteAlbumUseCase);

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

    @Override
    public AlbumResponse getById(final UUID id) {
        return AlbumApiPresenter.present(this.getAlbumByIdUseCase.execute(id));
    }

    @Override
    public Pagination<AlbumListResponse> listAlbums(
            String search, int page, int perPage, String sort, String direction
    ) {
        return this.listAlbumUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(AlbumApiPresenter::present);
    }

    @Override
    public ResponseEntity<?> updateById(final UUID id, final UpdateAlbumRequest input) {
        final var aCommand = UpdateAlbumCommand.with(id, input.titulo(), input.artistaId());

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateAlbumOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateAlbumUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final UUID id) {
        this.deleteAlbumUseCase.execute(id);
    }


}
