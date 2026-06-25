package com.ocs.portal.repository.customRepository.impl;

import com.ocs.portal.entity.OfferVer;

public interface OfferVerRepositoryCustom {
    boolean isOfferVerDateConflict(OfferVer dto);
    long checkWithMaxVer(OfferVer dto);
}
