package com.sts.sinorita.svc.role.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DirDto {
    private static final long serialVersionUID = -5233191665322635413L;

    private Long dirId;

    private Long parentId;

    @NotNull(message = "dirName not null")
    @Size(max = 255, message = "dirName max 255 ")
    private String dirName;

    @NotNull(message = "iconUrl not null")
    @Size(max = 255, message = "iconUrl max 255 ")
    private String iconUrl;

    private String state;

    private LocalDate stateDate;

}
