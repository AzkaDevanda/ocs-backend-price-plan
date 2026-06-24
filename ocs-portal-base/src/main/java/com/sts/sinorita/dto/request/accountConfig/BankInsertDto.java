package com.sts.sinorita.dto.request.accountConfig;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BankInsertDto {
    private Integer bankId;
    private Integer parentId;
    private String bankName;
    private String comments;
    private LocalDate stateDate;
    private Character directDebitFlag;
    private String bankCode;
    private Integer spId;
    private String countryCode;
    private String sepaAction;
    private String bic;
    private String ibanFormat;
}
