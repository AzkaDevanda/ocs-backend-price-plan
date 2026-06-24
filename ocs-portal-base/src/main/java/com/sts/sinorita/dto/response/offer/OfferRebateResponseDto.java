package com.sts.sinorita.dto.response.offer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferRebateResponseDto {
    
    Integer offerRebateId;
    Integer offerId;
    Integer offerVerId;
    Character rebateType;
    Integer rebateCount;
    Integer rebateSeq;
    Long value;
    String comments;
    Integer spId;
    String name;

}
