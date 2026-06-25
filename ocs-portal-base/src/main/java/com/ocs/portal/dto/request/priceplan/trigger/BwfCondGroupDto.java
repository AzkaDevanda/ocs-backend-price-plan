package com.ocs.portal.dto.request.priceplan.trigger;

import lombok.Data;

import java.util.List;

@Data
public class BwfCondGroupDto {
    private int spId;
    private List<BwfCondDto> bwfCondList;
}
