package com.seletivo.infra.persistence.regional;

import com.seletivo.domain.regional.Regional;
import com.seletivo.domain.regional.RegionalGateway;
import com.seletivo.domain.regional.RegionalID;
import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class RegionalPostgresSQLGateway implements RegionalGateway {

    private final RegionalRepository repository;

    public RegionalPostgresSQLGateway(final RegionalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Regional create(final Regional aRegional) {
        return save(aRegional);
    }

    @Override
    public Regional update(final Regional aRegional) {
        return save(aRegional);
    }

    @Override
    @Transactional
    public void saveAll(final List<Regional> regionais) {
        final var entities = regionais.stream()
                .map(RegionalJpaEntity::from)
                .toList();
        this.repository.saveAll(entities);
    }

    @Override
    public List<Regional> findAllActive() {
        return this.repository.findByAtivoTrue().stream()
                .map(RegionalJpaEntity::toAggregate)
                .toList();
    }

    @Override
    public Optional<Regional> findById(final RegionalID anId) {
        return this.repository.findById(anId.getValue().intValue())
                .map(RegionalJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Regional> findAll(final SearchQuery aQuery) {
        return null;
    }

    private Regional save(final Regional aRegional) {
        return this.repository.save(RegionalJpaEntity.from(aRegional)).toAggregate();
    }
}