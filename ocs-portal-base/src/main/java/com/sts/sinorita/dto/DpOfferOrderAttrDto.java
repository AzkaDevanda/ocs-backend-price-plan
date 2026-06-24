package com.sts.sinorita.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DpOfferOrderAttrDto {
    public Long dpOrderId;

    private Long attrId;

    @JsonProperty("attrValue")
    private String value;

    private String oldValue;

    private String operationType;

    private Long spId;

    private LocalDateTime effDate;

    private LocalDateTime expDate;

    private Long offerId;

    private String offerSeq;

    private String valueMark;

    private String attrName;

    private String oldValueMark;
}

