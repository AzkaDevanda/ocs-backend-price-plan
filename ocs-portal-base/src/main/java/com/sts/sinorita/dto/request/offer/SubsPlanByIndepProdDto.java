package com.sts.sinorita.dto.request.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubsPlanByIndepProdDto {
    private Integer subsPlanId;
    private Integer offerId;
    private String offerName;
    private String isBundleFlag;
    private String priority;
    private String saleFlag;
    private Integer spId;
    private Long indepProdSpecId;
    private String offerType;
    private String effDate;
    private String createdDate;
    private String offerCode;
    private String autoContinueFlag;
    private String state;
    private List<OfferVerForSubsPlanDto> offerVerList;
}
