package com.ocs.portal.dto.request.offer.commonoffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttrApplyChannelDto {
    private Integer attrId;
    private Integer contactChannelId;
    private Integer spId;
}
