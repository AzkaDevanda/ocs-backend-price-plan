package com.ocs.portal.svc.role.dto.request;

import lombok.Data;

@Data
public class BfmAttrDto {
    private static final long serialVersionUID = -465383639405126099L;

    public Long attrId;

    public String attrName;

    public String attrType;

    public Long objAttrId;

    public String attrCode;

    public String configVisible;

    public String csrVisible;

    public String instantiatable;

    public Long spId;
}
