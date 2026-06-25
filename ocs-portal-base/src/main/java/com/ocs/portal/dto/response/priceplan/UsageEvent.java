package com.ocs.portal.dto.response.priceplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageEvent {

    private Integer parentId;
    private Integer reId;
    private Character reType;
    private String reName;
    private String comments;
    private Integer spId;
    private String reCode;
    private Integer reAttr;

}
