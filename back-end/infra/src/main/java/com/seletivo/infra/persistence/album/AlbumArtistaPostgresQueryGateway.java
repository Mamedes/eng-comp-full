package com.seletivo.infra.persistence.album;

import com.seletivo.application.album.fetch.AlbumArtistaListView;
import com.seletivo.domain.album.AlbumArtistaQueryGateway;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class AlbumArtistaPostgresQueryGateway implements AlbumArtistaQueryGateway {

    private final AlbumRepository albumRepository;

    public AlbumArtistaPostgresQueryGateway(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Pagination<AlbumArtistaListView> findAll(SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var result = this.albumRepository.findAllWithArtistas(query.terms(), page);

        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(p -> new AlbumArtistaListView(
                        p.getAlbumSecureId(),
                        p.getAlbumTitulo(),
                        p.getArtistaNome(),
                        p.getArtistaTipo()
                )).toList()
        );
    }
}