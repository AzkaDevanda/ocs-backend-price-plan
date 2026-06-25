package com.ocs.portal.svc.role.dto.request.log;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LogDto {

    private Long logId;

    @NotNull(message = "logType not null")
    @Size(max = 12, message = "logType max 12 ")
    private String logType;

    @Size(max = 64, message = "serviceName max 64 ")
    private String serviceName;

    @Size(max = 64, message = "contactChannel max 64 ")
    private String contactChannel;

    private LocalDate logDate;

    @Size(max = 12, message = "subsId max 12 ")
    private String subsId;

    @Size(max = 12, message = "custId max 12 ")
    private String custId;

    @Size(max = 60, message = "custId max 60 ")
    private String eventSrc;

    @Size(max = 4000, message = "comments max 4000 ")
    private String comments;

    @Size(max = 30, message = "partyType max 30 ")
    private String partyType;

    @Size(max = 255, message = "partyCode max 255 ")
    private String partyCode;

    @Size(max = 255, message = "eventType max 255 ")
    private String eventType;

    @Size(max = 255, message = "eventCode max 255 ")
    private String eventCode;

    @Size(max = 30, message = "srcIp max 30 ")
    private String srcIp;

    private Long spId;

    private Long logTimestamp;

    private Long menuId;

    private String email;

    private String orderFields;

    private LocalDate beginDate;

    private LocalDate endDate;

    @Size(max = 60, message = "partyName max 60 ")
    private String partyName;

    private Long partyId;

    @Size(max = 255, message = "url max 255 ")
    private String url;

    private String menuType;

    @Size(max = 256, message = "privName max 256 ")
    private String privName;

    private int isSuccess;

    private Long duringTime;

    @Size(max = 30, message = "serverIp max 30 ")
    private String serverIp;

    private Long onlineTime;

    public int pageIndex;

    public int pageLen;

    public int rn;

    @Size(max = 60, message = "sessionId max 60 ")
    public String sessionId;

    @Size(max = 64, message = "bisTransId max 64 ")
    private String bisTransId;

    @Size(max = 266, message = "appName max 266 ")
    private String appName;

    private String menuName;

    private Integer portalId;

    private LocalDate lastOperTime;

    @NotNull(message = "Created By Mandatory")
    private String createdBy;
}
