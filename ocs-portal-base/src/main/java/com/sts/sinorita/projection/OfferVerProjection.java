package com.sts.sinorita.projection;

import java.time.LocalDate;

public interface OfferVerProjection {
    Integer getId();
    Integer getOfferId();
    LocalDate getEffDate();
    LocalDate getExpDate();
    Integer getSeq();
    Integer getSpId();
    Character getState();
    Integer getRefOfferVerId();
}
