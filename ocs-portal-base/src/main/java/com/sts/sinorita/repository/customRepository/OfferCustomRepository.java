package com.sts.sinorita.repository.customRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OfferCustomRepository {
    Page<Object[]> findAllWithDynamicFilter(
            Character applyLevel,
            Character type,
            String key,
            String value,
            String sort,
            String sortBy,
            Pageable pageable
    );
    Optional<Object[]> findOfferDetailByPricePlanIdAndApplyLevel(Integer pricePlanId, Character applyLevel);

}
