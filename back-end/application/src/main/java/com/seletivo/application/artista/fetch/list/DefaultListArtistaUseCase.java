package com.seletivo.application.artista.fetch.list;

import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import java.util.Objects;

public class DefaultListArtistaUseCase extends ListArtistaUseCase {
    private final ArtistaGateway artistaGateway;

    public DefaultListArtistaUseCase(final ArtistaGateway artistaGateway) {
        this.artistaGateway = Objects.requireNonNull(artistaGateway);
    }

    @Override
    public Pagination<ArtistaListOutput> execute(final SearchQuery aQuery) {
        return this.artistaGateway.findAll(aQuery)
                .map(ArtistaListOutput::from);
    }
}
