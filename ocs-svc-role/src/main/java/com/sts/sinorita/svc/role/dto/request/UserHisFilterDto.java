package com.sts.sinorita.svc.role.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserHisFilterDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private String userName;

}
