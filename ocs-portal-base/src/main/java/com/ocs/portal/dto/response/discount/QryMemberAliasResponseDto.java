package com.ocs.portal.dto.response.discount;

import lombok.Data;

@Data
public class QryMemberAliasResponseDto {
    private String offerName;
    private Integer offerId;
    private String offerVerName;
    private Integer offerVerId;
    private Integer bundleUnitId;
    private String memberAlias;
    private Integer indepProdSpecId;
}
