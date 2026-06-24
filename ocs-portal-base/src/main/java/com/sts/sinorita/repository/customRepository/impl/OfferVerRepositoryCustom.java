package com.sts.sinorita.repository.customRepository.impl;

import com.sts.sinorita.entity.OfferVer;

public interface OfferVerRepositoryCustom {
    boolean isOfferVerDateConflict(OfferVer dto);
    long checkWithMaxVer(OfferVer dto);
}
