package com.ocs.portal.svc.role.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class BfmStaffAttrDto {
    private static final long serialVersionUID = 5802834916222480807L;

    private Long staffId;
    private Long attrId;
    private String attrValue;
    private Long spId;
    private String attrCode;
    private String attrName;

    public BfmStaffAttrDto(Long staffId, Long attrId, String attrValue, Long spId) {
        this.staffId = staffId;
        this.attrId = attrId;
        this.attrValue = attrValue;
        this.spId = spId;
    }

}
