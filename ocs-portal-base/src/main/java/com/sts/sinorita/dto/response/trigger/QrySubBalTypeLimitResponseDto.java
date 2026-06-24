package com.sts.sinorita.dto.response.trigger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QrySubBalTypeLimitResponseDto {
    private Integer subBalTypeId;
    private Integer acctItemTypeId;
    private String acctItemTypeName;
}
