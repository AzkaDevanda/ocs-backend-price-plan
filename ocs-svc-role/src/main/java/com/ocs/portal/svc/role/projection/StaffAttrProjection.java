package com.ocs.portal.svc.role.projection;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public interface StaffAttrProjection {
    Long getStaffId();
    Long getAttrId();
    String getAttrValue();
    Long getSpId();
    String getAttrCode();
    String getAttrName();
}
