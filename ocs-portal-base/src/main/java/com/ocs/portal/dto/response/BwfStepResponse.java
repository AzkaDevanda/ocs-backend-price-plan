package com.ocs.portal.dto.response;

import com.ocs.portal.dto.request.priceplan.trigger.BwfActionDto;
import com.ocs.portal.dto.request.priceplan.trigger.BwfCondGroupDto;
import com.ocs.portal.dto.request.priceplan.trigger.BwfSysActionDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BwfStepResponse {

    private Integer id;

    private Integer nodeId;

    private Integer outputNodeId;

    private String sortRuleName;

    private String comments;

    private Integer execOrder;

    private LocalDate effDate;

    private LocalDate expDate;

    private Integer spId;

    private List<BwfCondGroupDto> bwfCondGroupList;
    private List<BwfActionDto> bwfActionList;
    private BwfSysActionDto bwfSysActionDto;
}
