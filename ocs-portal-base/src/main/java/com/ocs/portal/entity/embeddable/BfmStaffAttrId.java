package com.ocs.portal.entity.embeddable;


import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class BfmStaffAttrId implements Serializable {
    private Long staffId;
    private String attrCode;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BfmStaffAttrId)) return false;
        BfmStaffAttrId that = (BfmStaffAttrId) o;
        return Objects.equals(staffId, that.staffId) && Objects.equals(attrCode, that.attrCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staffId, attrCode);
    }
}
