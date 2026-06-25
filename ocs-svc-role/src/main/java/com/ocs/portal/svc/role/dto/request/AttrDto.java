package com.ocs.portal.svc.role.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrDto implements Serializable {

    private String attrId;

    private String attrCode;

    private String attrValue;

    private String displayAble;
}
