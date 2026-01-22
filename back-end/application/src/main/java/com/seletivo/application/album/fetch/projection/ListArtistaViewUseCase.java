package com.seletivo.application.album.fetch.projection;

import com.seletivo.application.UseCase;
import com.seletivo.application.artista.fetch.list.view.ArtistaListView;
import com.seletivo.domain.artista.ArtistaQueryGateway;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;

import java.util.Objects;

public class ListArtistaViewUseCase
        extends UseCase<SearchQuery, Pagination<ArtistaListViewOutput>> {

    private final ArtistaQueryGateway artistaQueryGateway;

    public ListArtistaViewUseCase(final ArtistaQueryGateway artistaQueryGateway) {
        this.artistaQueryGateway = Objects.requireNonNull(artistaQueryGateway);
    }

    @Override
    public Pagination<ArtistaListViewOutput> execute(final SearchQuery aQuery) {
        final Pagination<ArtistaListView> domainPagination =
                this.artistaQueryGateway.findAll(aQuery);

        return domainPagination.map(domain -> new ArtistaListViewOutput(
                domain.getSecureId(),
                domain.getNome(),
                domain.getTipo(),
                domain.getQuantidadeAlbuns()
        ));
    }
}