package com.seletivo.infra.persistence.album;

import com.seletivo.domain.album.Album;
import com.seletivo.domain.album.AlbumGateway;
import com.seletivo.domain.album.AlbumID;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AlbumPostgresSQLGateway implements AlbumGateway {

    private final AlbumRepository repository;

    public AlbumPostgresSQLGateway(final AlbumRepository repository) {
        this.repository = repository;
    }

    @Override
    public Album create(final Album anAlbum) {
        return save(anAlbum);
    }

    @Override
    @Transactional
    public void deleteBySecureId(final UUID aSecureId) {
        this.repository.deleteBySecureId(aSecureId);
    }

    @Override
    public Optional<Album> findById(final AlbumID anId) {
        return this.repository.findById(anId.getValue())
                .map(AlbumJpaEntity::toAggregate);
    }

    @Override
    public Optional<Album> findBySecureId(final UUID aSecureId) {
        return this.repository.findBySecureId(aSecureId)
                .map(AlbumJpaEntity::toAggregate);
    }

    @Override
    public Album update(final Album anAlbum) {
        return save(anAlbum);
    }

    @Override
    public Pagination<Album> findAll(final SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var pageResult = this.repository.findAll(page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(AlbumJpaEntity::toAggregate).toList()
        );
    }

    private Album save(final Album anAlbum) {
        return this.repository.saveAndFlush(AlbumJpaEntity.from(anAlbum))
                .toAggregate();
    }

    @Override
    public List<Album> findAllByArtistaId(final UUID artistaId) {
        return this.repository.findAllByArtistaSecureId(artistaId).stream()
                .map(AlbumJpaEntity::toAggregate)
                .toList();
    }
}