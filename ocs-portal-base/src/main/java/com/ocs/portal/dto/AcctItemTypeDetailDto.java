package com.ocs.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcctItemTypeDetailDto {
    private String balTypeName;
    private Integer acctItemTypeId;
    private Integer balType;
    private Integer billPriority;
    private String acctResName;
    private Integer acctResId;
    private Character isCurrency;
    private String acctItemTypeName;
}
