package com.ocs.portal.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubEventTriggerDto {
    private Integer subEventId;
    private String subsEventName;
    private String priceplanCode;
    private String triggerMode;
    private Integer paramId;
    private Integer orderTime;
    private Integer days;
    private Character eventCatG;
    private Character objProdState;

}
