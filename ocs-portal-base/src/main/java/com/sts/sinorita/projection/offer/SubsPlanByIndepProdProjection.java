package com.sts.sinorita.projection.offer;

import java.time.LocalDate;

public interface SubsPlanByIndepProdProjection {
        Integer getSubsPlanId();
        Integer getOfferId();
        String getOfferName();
        String getIsBundleFlag();
        String getPriority();
        String getSaleFlag();
        Integer getSpId();
        Long getIndepProdSpecId();
        String getOfferType();
        String getEffDate();
        String getCreatedDate();
        String getOfferCode();
        String getAutoContinueFlag();
        String getState();

        // Nested offerVer
        Integer getOfferVerId();
        String getSeq();
        String getOfferVerEffDate();
}
