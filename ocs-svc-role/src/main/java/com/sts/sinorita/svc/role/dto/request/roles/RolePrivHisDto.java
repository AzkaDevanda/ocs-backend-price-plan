package com.sts.sinorita.svc.role.dto.request.roles;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RolePrivHisDto extends RolePrivDto {

    private Long recId;

    private String operatorType;

    private LocalDate updateDate;

    private LocalDate createdDate;

}
