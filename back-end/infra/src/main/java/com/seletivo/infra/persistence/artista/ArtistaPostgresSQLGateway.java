package com.seletivo.infra.persistence.artista;

import com.seletivo.domain.artista.Artista;
import com.seletivo.domain.artista.ArtistaGateway;
import com.seletivo.domain.artista.ArtistaID;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArtistaPostgresSQLGateway implements ArtistaGateway {

    private final ArtistaRepository repository;

    public ArtistaPostgresSQLGateway(final ArtistaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Artista create(final Artista anArtista) {
        return save(anArtista);
    }

    @Override
    public void deleteById(final ArtistaID anId) {
        if (this.repository.existsById(anId.getValue())) {
            this.repository.deleteById(anId.getValue());
        }
    }

    @Override
    public Optional<Artista> findById(final ArtistaID anId) {
        return this.repository.findById(anId.getValue())
                .map(ArtistaJpaEntity::toAggregate);
    }

    @Override
    public Artista update(final Artista anArtista) {
        return save(anArtista);
    }

    @Override
    public Pagination<Artista> findAll(final SearchQuery aQuery) {
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
                pageResult.map(ArtistaJpaEntity::toAggregate).toList()
        );
    }

    private Artista save(final Artista anArtista) {
        return this.repository.save(ArtistaJpaEntity.from(anArtista)).toAggregate();
    }
}