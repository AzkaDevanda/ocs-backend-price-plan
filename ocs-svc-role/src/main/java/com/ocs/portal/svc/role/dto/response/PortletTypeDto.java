package com.ocs.portal.svc.role.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PortletTypeDto {
    private static final long serialVersionUID = -9032495653097133581L;

    private Long typeId;

    private String typeName;

    private String comments;

    private String state;

    private LocalDate stateDate;

    private String name;

    private Long type;

}
