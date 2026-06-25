package com.ocs.portal.svc.role.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class RoleDto {

    private static final long serialVersionUID = -6824784004685697976L;
    private Long roleId;

    @NotNull(message = "roleName not null")
    @Size(max = 60, message = "roleName max 60 ")
    private String roleName;

    @Size(max = 255, message = "comments max 255 ")
    private String comments;

    @NotNull(message = "roleCode not null")
    @Size(max = 60, message = "roleCode max 60 ")
    private String roleCode;

    private String isLocked;

    private Long appId;

    private String orderFields;

    private Long spId;

    private Long createdId;

    private LocalDate createdDate;

    private Long portalId;

}
