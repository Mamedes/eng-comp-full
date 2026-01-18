package com.seletivo.infra.api.controller.artista;

import com.seletivo.application.artista.create.CreateArtistaCommand;
import com.seletivo.application.artista.create.CreateArtistaOutput;
import com.seletivo.application.artista.create.CreateArtistaUseCase;
import com.seletivo.application.artista.fetch.get.GetArtistaByIdUseCase;
import com.seletivo.application.artista.fetch.list.ListArtistaUseCase;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import com.seletivo.domain.validation.handler.Notification;
import com.seletivo.infra.api.controller.artista.presenters.ArtistaApiPresenter;
import com.seletivo.infra.api.controller.artista.request.CreateArtistaRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class ArtistaController implements ArtistaAPI {

    private final CreateArtistaUseCase createArtistaUseCase;
    private final GetArtistaByIdUseCase getArtistaByIdUseCase;
    private final ListArtistaUseCase  listArtistaUseCase;

    public ArtistaController(
            final CreateArtistaUseCase createArtistaUseCase,
            final GetArtistaByIdUseCase getArtistaByIdUseCase,
            final ListArtistaUseCase listArtistaUseCase
    ) {
        this.createArtistaUseCase = Objects.requireNonNull(createArtistaUseCase);
        this.getArtistaByIdUseCase = Objects.requireNonNull(getArtistaByIdUseCase);
        this.listArtistaUseCase =Objects.requireNonNull(listArtistaUseCase);
    }

    @Override
    public ResponseEntity<?> createArtista(final CreateArtistaRequest input) {
        final var aCommand = CreateArtistaCommand.with(input.nome(), input.tipo());

        final Function<Notification, ResponseEntity<?>> onError =
                notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateArtistaOutput, ResponseEntity<?>> onSuccess =
                output -> ResponseEntity.created(URI.create("/artista/" + output.id())).body(output);

        return this.createArtistaUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public ArtistaResponse getById(final Long id) {
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
}