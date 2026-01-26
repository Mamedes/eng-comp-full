package com.seletivo.domain.regional;

import com.seletivo.domain.pagination.Pagination;
import com.seletivo.domain.pagination.SearchQuery;
import java.util.List;
import java.util.Optional;

public interface RegionalGateway {
    Regional create(Regional aRegional);
    Regional update(Regional aRegional);
    void saveAll(List<Regional> regionais);
    Optional<Regional> findById(RegionalID anId);
    List<Regional> findAllActive();
    Pagination<Regional> findAll(SearchQuery aQuery);
}