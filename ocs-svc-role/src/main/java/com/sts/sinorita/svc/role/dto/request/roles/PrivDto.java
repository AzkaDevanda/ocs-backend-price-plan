package com.sts.sinorita.svc.role.dto.request.roles;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PrivDto {

    private Long privId;

    @NotNull(message = "privType not null")
    private String privType;

    @NotNull(message = "privName not null")
    @Size(max = 60, message = "privName max 255 ")
    private String privName;

    @Size(max = 255, message = "comments max 255 ")
    private String comments;

    @NotNull(message = "url not null")
    @Size(max = 4000, message = "url max 4000 ")
    private String url;

    private String state;

    private LocalDate stateDate;

    @Size(max = 60, message = "privCode max 255 ")
    private String privCode;

    @Size(max = 4000, message = "privEl max 4000 ")
    private String privEl;

    private Long menuPrivId;

    @Size(max = 255, message = "cdnUrl max 255 ")
    private String cdnUrl;

    @Size(max = 255, message = "jsFile max 255 ")
    private String jsFile;

    @Size(max = 255, message = "cssFile max 255 ")
    private String cssFile;
}
