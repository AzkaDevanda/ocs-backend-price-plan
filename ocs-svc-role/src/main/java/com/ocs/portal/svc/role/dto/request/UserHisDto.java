package com.ocs.portal.svc.role.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserHisDto {
    private static final long serialVersionUID = -5382808076091108592L;

    private Long recId;

    private Long userId;

    private String userName;

    private String userCode;

    @JsonIgnore
    private String pwd;

    private String address;

    private String memo;

    private LocalDate userEffDate;

    private LocalDate userExpDate;

    private LocalDate createdDate;

    private String state;

    private LocalDate stateDate;

    private String isLocked;

    private LocalDate pwdExpDate;

    private Long loginFail;

    private Long portalId;

    private Long recUserId;

    private LocalDate recCreateDate;

    private String ipAddress;

    private String comments;

    private LocalDate unlockDate;

    private String portalName;

    private String recUserName;

    private String recUserCode;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long spId;

    private String operatorType;

    private String orderFields;
}
