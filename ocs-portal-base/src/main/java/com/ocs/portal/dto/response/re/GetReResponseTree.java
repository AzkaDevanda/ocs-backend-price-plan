package com.ocs.portal.dto.response.re;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetReResponseTree {
    private BigDecimal parentId;
    private BigDecimal reId;
    private Character reType;
    private String reName;
    private String comments;
    private BigDecimal spId;
    private String reCode;
    private BigDecimal reAttr;

    private List<GetReResponseTree> children = new ArrayList<>();
}
