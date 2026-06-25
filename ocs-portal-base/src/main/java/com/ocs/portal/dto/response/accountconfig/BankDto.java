package com.ocs.portal.dto.response.accountconfig;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BankDto {
    private Integer bankId;
    private Integer parentId;
    private String bankName;
    private String comments;
    private String bankCode;
    private String state;
    private LocalDate stateDate;
    private Integer spId;
    private String directDebitFlag;
    private String bic;
    private String ibanFormat;
    private Integer child;
}
