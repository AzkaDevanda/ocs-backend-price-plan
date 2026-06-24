package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcctItemTypeListDto {
    private String acctItemTypeName;
    private String acctResName;
    private String balCategory;
}
