package com.ocs.portal.svc.role.dto.request.job;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
public class JobDto {

    private Long jobId;

    @NotNull(message = "jobName not null")
    @Size(max = 255,message = "jobName max 255 ")
    private String jobName;

    @NotNull(message = "state not null")
    @Size(max = 1,message = "state max 1 ")
    private Character state;

    private LocalDate stateDate;

    private Long spId;

    private String jobCode;

    private String isAuthorized;

    private String isDefault;

    private String securityLevel;

    private Long parJobId;

    private LocalDate updateDate;

    private LocalDate createdDate;

    private String jobNameCode;
}
