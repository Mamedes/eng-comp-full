package com.seletivo.infra.persistence.artista;

import com.seletivo.application.artista.fetch.list.view.ArtistaListView;
import com.seletivo.domain.artista.ArtistaQueryGateway;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ArtistaPostgresSQLQueryGateway
        implements ArtistaQueryGateway<ArtistaListView> {

    private final ArtistaRepository artistaRepository;

    public ArtistaPostgresSQLQueryGateway(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @Override
    public Pagination<ArtistaListView> findAll(SearchQuery query) {

        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var result = artistaRepository.findAllWithAlbumCount(query.terms(), page);

        final var items = result.map(p ->
                new ArtistaListView(
                        p.getSecureId(),
                        p.getNome(),
                        p.getTipo(),
                        p.getQuantidadeAlbuns()
                )
        ).toList();

        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                items
        );
    }
}
