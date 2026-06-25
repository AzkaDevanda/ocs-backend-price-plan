package com.ocs.portal.dto.response.discount;

import lombok.Data;

@Data
public class QryDisctObjInfoDto {
    private Integer disctObjId;
    private Integer acctItemTypeId;
    private Integer priority;
    private Integer spId;
    private String acctItemTypeName;
}
