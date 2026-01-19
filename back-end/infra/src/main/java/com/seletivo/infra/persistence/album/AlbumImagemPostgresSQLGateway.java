package com.seletivo.infra.persistence.album;

import com.seletivo.domain.album.AlbumImagem;
import com.seletivo.domain.album.AlbumImagemGateway;
import com.seletivo.domain.album.AlbumImagemID;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AlbumImagemPostgresSQLGateway implements AlbumImagemGateway {

    private final AlbumImagemRepository repository;

    public AlbumImagemPostgresSQLGateway(final AlbumImagemRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public AlbumImagem create(final AlbumImagem anAlbumImagem) {
        return save(anAlbumImagem);
    }

    @Override
    public AlbumImagem update(final AlbumImagem anAlbumImagem) {
        return save(anAlbumImagem);
    }

    private AlbumImagem save(final AlbumImagem anAlbumImagem) {
        return this.repository.save(AlbumImagemJpaEntity.from(anAlbumImagem)).toAggregate();
    }

    @Override
    public void deleteById(final AlbumImagemID anId) {
        if (this.repository.existsById(anId.getValue())) {
            this.repository.deleteById(anId.getValue());
        }
    }

    @Override
    public Optional<AlbumImagem> findById(final AlbumImagemID anId) {
        return this.repository.findById(anId.getValue()).map(AlbumImagemJpaEntity::toAggregate);
    }

    @Override
    public Pagination<AlbumImagem> findAll(final SearchQuery aQuery) {
        return null;
    }

    @Override
    public List<AlbumImagemID> existsByIds(final Iterable<AlbumImagemID> ids) {
        return List.of();
    }

    @Override
    public Optional<AlbumImagem> findBySecureId(final UUID anId) {
        return this.repository.findBySecureId(anId).map(AlbumImagemJpaEntity::toAggregate);
    }

    @Override
    public List<AlbumImagem> findByAlbumId(final Long albumId) {
        return this.repository.findAllByAlbumId(albumId)
                .stream()
                .map(AlbumImagemJpaEntity::toAggregate)
                .collect(Collectors.toList());
    }
}