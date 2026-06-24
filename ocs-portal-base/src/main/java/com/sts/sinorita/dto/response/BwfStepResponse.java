package com.sts.sinorita.dto.response;

import com.sts.sinorita.dto.request.priceplan.trigger.BwfActionDto;
import com.sts.sinorita.dto.request.priceplan.trigger.BwfCondGroupDto;
import com.sts.sinorita.dto.request.priceplan.trigger.BwfSysActionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
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
