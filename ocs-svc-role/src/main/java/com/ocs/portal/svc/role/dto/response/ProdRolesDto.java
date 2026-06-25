package com.ocs.portal.svc.role.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProdRolesDto {

    private Long roleId;

    @NotNull(message = "roleName not null")
    @Size(max = 60, message = "roleName max 60 ")
    private String roleName;

    @Size(max = 255, message = "comments max 255 ")
    private String comments;

    private String roleCode;

    private String isLocked;

    private Long appId;

    private Long spId;

    private LocalDate createdDate;

    private LocalDate updateDate;

    private String orderFields;
}
