package com.seletivo.infra.api.controller.artista;

import com.seletivo.application.album.fetch.projection.ArtistaListViewOutput;
import com.seletivo.application.album.fetch.projection.ListArtistaViewUseCase;
import com.seletivo.application.artista.create.CreateArtistaCommand;
import com.seletivo.application.artista.create.CreateArtistaOutput;
import com.seletivo.application.artista.create.CreateArtistaUseCase;
import com.seletivo.application.artista.delete.DeleteArtistaUseCase;
import com.seletivo.application.artista.fetch.details.GetArtistaDetalheUseCase;
import com.seletivo.application.artista.fetch.get.GetArtistaByIdUseCase;
import com.seletivo.application.artista.fetch.list.ListArtistaUseCase;
import com.seletivo.application.artista.update.UpdateArtistaCommand;
import com.seletivo.application.artista.update.UpdateArtistaOutput;
import com.seletivo.application.artista.update.UpdateArtistaUseCase;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import com.seletivo.domain.validation.handler.Notification;
import com.seletivo.infra.api.controller.artista.presenters.ArtistaApiPresenter;
import com.seletivo.infra.api.controller.artista.presenters.ArtistaDetalheApiPresenter;
import com.seletivo.infra.api.controller.artista.request.CreateArtistaRequest;
import com.seletivo.infra.api.controller.artista.request.UpdateArtistaRequest;
import com.seletivo.infra.api.controller.artista.response.ArtistaDetalheResponse;
import com.seletivo.infra.api.controller.artista.response.ArtistaListResponse;
import com.seletivo.infra.api.controller.artista.response.ArtistaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@RestController
public class ArtistaController implements ArtistaAPI {

    private final CreateArtistaUseCase createArtistaUseCase;
    private final GetArtistaByIdUseCase getArtistaByIdUseCase;
    private final ListArtistaUseCase  listArtistaUseCase;
    private final UpdateArtistaUseCase updateArtistaUseCase;
    private final DeleteArtistaUseCase deleteArtistaUseCase;
    private final ListArtistaViewUseCase listArtistaDashboardUseCase;
    private final ArtistaDetalheApiPresenter artistaDetalheApiPresenter;
    private final GetArtistaDetalheUseCase getArtistaDetalheUseCase;

    public ArtistaController(
            final CreateArtistaUseCase createArtistaUseCase,
            final GetArtistaByIdUseCase getArtistaByIdUseCase,
            final ListArtistaUseCase listArtistaUseCase,
            final UpdateArtistaUseCase updateArtistaUseCase,
            final DeleteArtistaUseCase deleteArtistaUseCase,
            final ListArtistaViewUseCase listArtistaDashboardUseCase,
            final GetArtistaDetalheUseCase getArtistaDetalheUseCase,
            final ArtistaDetalheApiPresenter artistaDetalheApiPresenter
    ) {
        this.createArtistaUseCase = Objects.requireNonNull(createArtistaUseCase);
        this.getArtistaByIdUseCase = Objects.requireNonNull(getArtistaByIdUseCase);
        this.listArtistaUseCase = Objects.requireNonNull(listArtistaUseCase);
        this.updateArtistaUseCase = Objects.requireNonNull(updateArtistaUseCase);
        this.deleteArtistaUseCase = Objects.requireNonNull(deleteArtistaUseCase);
        this.listArtistaDashboardUseCase = Objects.requireNonNull(listArtistaDashboardUseCase);
        this.getArtistaDetalheUseCase = Objects.requireNonNull(getArtistaDetalheUseCase);
        this.artistaDetalheApiPresenter = Objects.requireNonNull(artistaDetalheApiPresenter);
    }

    @Override
    public ResponseEntity<?> createArtista(final CreateArtistaRequest input) {
        final var aCommand = CreateArtistaCommand.with(input.nome(), input.tipo());

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateArtistaOutput, ResponseEntity<?>> onSuccess =
                output -> ResponseEntity.created(URI.create("/artista/" + output.artistaId())).body(output);

        return this.createArtistaUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public ArtistaResponse getById(final UUID id) {
        return ArtistaApiPresenter.present(this.getArtistaByIdUseCase.execute(id));
    }

    @Override
    public Pagination<ArtistaListResponse> listArtistas(
            String search,
            int page,
            int perPage,
            String sort,
            String direction
    ) {
        return listArtistaUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(ArtistaApiPresenter::present);
    }

    @Override
    public ResponseEntity<?> updateById(UUID id, UpdateArtistaRequest input) {
        final var aCommand =
                UpdateArtistaCommand.with(id, input.nome(), input.tipo());

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateArtistaOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateArtistaUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public void deleteById(UUID id) {
        deleteArtistaUseCase.execute(id);
    }

    @Override
    public Pagination<ArtistaListViewOutput> listArtistasDashboard(
            String search, int page, int perPage, String sort, String direction
    ) {
        return this.listArtistaDashboardUseCase.execute(
                new SearchQuery(page, perPage, search, sort, direction)
        );
    }

    @Override
    public ArtistaDetalheResponse getDetalheById(final UUID id) {
        return this.artistaDetalheApiPresenter.present(this.getArtistaDetalheUseCase.execute(id));
    }


}