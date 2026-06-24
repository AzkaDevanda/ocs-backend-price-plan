package com.sts.sinorita.dto.response.offer;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class OfferSubPlanResponse implements Serializable {
    private Integer subsPlanId;
    private String subsPlanName;
    private Integer indepProdSpecId;
    private Integer offerVerId;
    private LocalDate effDate;
    private LocalDate expDate;
    private Integer  offerGroupId;
}
